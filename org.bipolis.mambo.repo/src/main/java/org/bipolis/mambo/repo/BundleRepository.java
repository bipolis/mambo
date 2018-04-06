package org.bipolis.mambo.repo;

import static org.osgi.service.component.annotations.ConfigurationPolicy.REQUIRE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bipolis.mambo.repo.rsw.ObrRepositoryRessource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.indexer.Capability;
import org.osgi.service.indexer.Namespaces;
import org.osgi.service.indexer.Resource;
import org.osgi.service.indexer.ResourceIndexer;
import org.osgi.service.indexer.impl.BundleAnalyzer;
import org.osgi.service.indexer.impl.JarResource;
import org.osgi.service.indexer.impl.RepoIndex;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OSGi Bundle Repository to stores Bundles in lokale Filesystem.
 * <p>
 * Obervates a configures lokale Directory {@link Config#repoDir()} using a {@link WatchService} and
 * generating a Index-File {@value #INDEX_FILE} for each change in directory or subdirectory
 * </p>
 * <p>
 * The Bundle-Entries of the Index-Files are build with the configured {@link Config#repoUrl()}. The
 * Repository is published by a jaxrs ressource {@link ObrRepositoryRessource} under this URL.
 * </p>
 *
 */
@Designate(ocd = org.bipolis.mambo.repo.BundleRepository.Config.class,factory=true)
@Component(immediate = true, service = BundleRepository.class, configurationPolicy = REQUIRE)
public class BundleRepository {


  @ObjectClassDefinition(
          name = "Bundle Repository",
          description = "Configuration of the Bundle Repository")
  public @interface Config {

    @AttributeDefinition(
            name = "Repository Name",
            description = "Absolute path to the repository in the server's local file system")
    String name();

    @AttributeDefinition(
            name = "Repository Directory",
            description = "Absolute path to the repository in the server's local file system")
    String dir();
//
//
//    @AttributeDefinition(name = "override", description = "")
//    boolean override();
//
//
//    @AttributeDefinition(name = "subDirs", description = "")
//    String[] subDirs();
//
//    @AttributeDefinition(name = "bundlesOnly", description = "")
//    boolean bundlesOnly();
//
//    @AttributeDefinition(name = "snapshots", description = "")
//    boolean snapshots();
//
//    @AttributeDefinition(name = "externalDeploy", description = "")
//    boolean externalDeploy();
//
//
//    @AttributeDefinition(name = "deployOverRepositorys", description = "")
//    String[] deployOverRepositorys();


  }

  /** Name of the Index-Files */
  public static final String INDEX_FILE = "index.xml";

  /** Extension of JAR-File (Bundles) */
  public static final String EXT_JAR = ".jar";

  /** Extension of EAS-File (Subsystems) */
  public static final String EXT_EAS = ".eas";

  /** Algorithm to calculate the Bundle-Hashs */
  public static final String DIGEST_ALGORITHM = "SHA-1";

  private static final Logger logger = LoggerFactory.getLogger(BundleRepository.class);

  private final AtomicBoolean stale = new AtomicBoolean(true);
  private final RepoIndex indexer = new RepoIndex();
  private final Map<String, String> indexerConfig = new HashMap<>();
  private final Map<String, WatchKey> watchKeys = new ConcurrentHashMap<>();

  private Path repoRoot;
  private WatchService watchService;
  private LogService log;
  private ScheduledFuture<?> indexerExecutor;
  private DirWatcher dirWatcher;


  /**
   * Add a Bundle to a Repository.
   *
   * @param src Source-Stream for the Content of Bundles
   * @param path Optional target-path in the Repository; if <code>null</code> Bundle will stored in
   *        the root of the Repository
   * @return Information to added Bundle
   * @throws BundleRepositoryException when while adding a Bundle a Error happened
   */
  public RepositoryAdditionResult addBundle(final InputStream src,
                                            final String path)
          throws BundleRepositoryException {
    final Path tmp;

    if (src == null) {
      throw new BundleRepositoryException(
              new IllegalArgumentException("Bundle source stream is null"));
    }

    MessageDigest messageDigest = null;

    try {
      messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
    } catch (final NoSuchAlgorithmException nsae) {
      throw new BundleRepositoryException(nsae);
    }

    byte[] digest;

    // copy from Stream to a temporary file
    try (DigestInputStream in = new DigestInputStream(src, messageDigest)) {
      tmp = Files.createTempFile("bundle", EXT_JAR);
      logger.debug("temp file: " + tmp);

      Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);

      digest = in.getMessageDigest()
                 .digest();
    } catch (final IOException ioe) {
      throw new BundleRepositoryException("Error while receiving the bundle", ioe);
    }

    // analyse Bundle Meta-Data
    final BundleAnalyzer analyzer = new BundleAnalyzer(log);
    final List<Capability> capabilities = new ArrayList<>();
    String bundleId = null;
    String bundleVersion = null;
    String url = null;
    Resource resource = null;

    try {
      resource = new JarResource(tmp.toFile());
      analyzer.analyzeResource(resource, capabilities, new ArrayList<>());

      for (final Capability c : capabilities) {
        if (Namespaces.NS_IDENTITY.equals(c.getNamespace())) {
          bundleId = c.getAttributes()
                      .get(Namespaces.NS_IDENTITY)
                      .toString();
          bundleVersion = c.getAttributes()
                           .get(Namespaces.ATTR_VERSION)
                           .toString();
        }
      }


      if (logger.isDebugEnabled()) {
        final StringBuilder sb = new StringBuilder("analyzed bundle capabilities:");
        capabilities.forEach(c -> {
          sb.append("\n\t")
            .append(c.getNamespace());
          c.getAttributes()
           .entrySet()
           .forEach(e -> sb.append("\n\t\t")
                           .append(e.getKey())
                           .append(" = ")
                           .append(e.getValue()));
        });
        logger.debug(sb.toString());
      }


      if (bundleId == null) {
        throw new IllegalStateException("Bundle contains no ID");
      }

      if (bundleVersion == null) {
        throw new IllegalStateException("Bundle contains no version");
      }

      url = indexerConfig.get(ResourceIndexer.URL_TEMPLATE)
                         .replace("%p", path.isEmpty() ? "" : path + "/")
                         .replace("%f", bundleId + "-" + bundleVersion + EXT_JAR);

      if (url == null) {
        throw new IllegalStateException("Bundle URL could not be determined");
      }

      logger.info("Adding bundle " + bundleId + " version " + bundleVersion
              + " to the repository at " + (path == null || path.trim()
                                                                .isEmpty() ? "root" : path));
    } catch (final Exception e) {
      throw new BundleRepositoryException("Error while analyzing the bundle", e);
    } finally {
      if (resource != null) {
        resource.close();
      }
    }

    // copy Bundle in normalized name into Repo-Structure
    try {
      final Path bundleDir = repoRoot.resolve(path == null ? "" : path);
      Files.createDirectories(bundleDir);

      final Path bundle = bundleDir.resolve(bundleId + "-" + bundleVersion + EXT_JAR);

      if (!Files.exists(bundle)) {
        Files.createFile(bundle);
      }

      Files.copy(tmp, bundle, StandardCopyOption.REPLACE_EXISTING);

      logger.debug("Bundle created: " + bundle);

      return new RepositoryAdditionResult(true, url, digest);
    } catch (final IOException ioe) {
      throw new BundleRepositoryException("Error while creating the bundle in the repository", ioe);
    } finally {
      try {
        Files.delete(tmp);
      } catch (final IOException ioe) {
        logger.warn("Error while deleting the temporary bundle file: " + tmp, ioe);
      }
    }
  }


  /**
   * gives access to Index-File.
   *
   * @param path relativer Path to Index-File
   * @return die Index-File
   * @throws BundleRepositoryException if there is any error while accessing Index-File
   */
  public Path indexFile(final String path)
          throws BundleRepositoryException {
    final String indexPath;
    if (path == null || path.trim()
                            .isEmpty()) {
      indexPath = INDEX_FILE;
    } else {
      indexPath = path.toLowerCase()
                      .endsWith(INDEX_FILE) ? path : path + "/" + INDEX_FILE;
    }

    try {
      final Path indexFile = repoRoot.resolve(indexPath);

      if (!Files.isRegularFile(indexFile)) {
        return null;
      }

      return indexFile;
    } catch (final RuntimeException rte) {
      throw new BundleRepositoryException("Error while accessing the index file", rte);
    }
  }


  /**
   * gives access to a Bundle-File specified by the relative path from the root of the Repositories.
   *
   * @param bundlePath relative path of the Bundle
   * @return die Bundle-File
   * @throws BundleRepositoryException Error while accessing the index file
   */
  public Path bundleFile(final String bundlePath)
          throws BundleRepositoryException {
    final Path bundleFile = repoRoot.resolve(bundlePath);

    if (Files.isRegularFile(bundleFile)) {
      return bundleFile;
    }

    return null;
  }


  /**
   * Triggers a re-indexing of the repository
   * <p>
   * In each directory-level a new {@value #INDEX_FILE} will be created. Each folder under the root
   * has represents a separate full usable repository of all files in the directory an its
   * sub-directories.
   * </p>
   */
  public void updateIndex() {
    try {
      Files.walkFileTree(repoRoot, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(final Path dir,
                                                 final BasicFileAttributes attrs)
                throws IOException {
          updateIndex(dir);
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (final IOException ioe) {
      logger.error(ioe.getMessage(), ioe);
    }
  }


  private void updateIndex(final Path root) {
    final Path indexFile = root.resolve(INDEX_FILE);

    if (Files.notExists(indexFile)) {
      try {
        Files.createFile(indexFile);
      } catch (final IOException ioe) {
        logger.error("Error while creating index file " + indexFile, ioe);
      }
    }

    try (OutputStream out = new FileOutputStream(indexFile.toFile())) {
      indexer.index(repoFiles(root), out, indexerConfig);
    } catch (final Exception e) {
      logger.error(e.getMessage(), e);
      return;
    }

    logger.info("index updated: " + indexFile);
  }


  private void registerDirs(final Path root)
          throws IOException {
    // check if root-directory is readable
    if (!Files.isReadable(root)) {
      // waiting 1s and retry (Operationen, that triggers Watcher-Events, are not atomar.)
      try {
        Thread.sleep(1000);
      } catch (final InterruptedException ie) {
        // no op
      }

      if (Files.isReadable(root) && Files.isDirectory(root)) {
        throw new IOException("Directory " + root + " not readable");
      }
    }

    Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(final Path dir,
                                               final BasicFileAttributes attrs)
              throws IOException {
        watchKeys.put(dir.toString(),
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY));
        logger.info("watching " + dir);

        return FileVisitResult.CONTINUE;
      }
    });
  }


  private Set<File> repoFiles(final Path root)
          throws IOException {
    final Set<File> jars = new HashSet<>();

    Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(final Path file,
                                       final BasicFileAttributes attrs)
              throws IOException {
        if (Files.isRegularFile(file) && file.getFileName()
                                             .toString()
                                             .toLowerCase()
                                             .endsWith(EXT_JAR)) {
          jars.add(file.toFile());
        }

        return FileVisitResult.CONTINUE;
      }
    });

    return jars;
  }


  private class DirWatcher implements Runnable {

    private boolean stop = false;


    @Override
    public void run() {
      while (!stop) {
        final WatchKey key;

        try {
          // blicking until event happened
          key = watchService.take();
        } catch (final InterruptedException ie) {
          logger.error(ie.getMessage(), ie);
          break;
        }

        if (!key.equals(watchKeys.get(key.watchable()
                                         .toString()))) {
          // unknown Key, ignore and cancel
          logger.debug("unknown watch key: " + key + " for " + key.watchable());
          key.cancel();
          continue;
        }

        // Get ans handle evends fore each key
        for (final WatchEvent<?> event : key.pollEvents()) {
          if (StandardWatchEventKinds.OVERFLOW.equals(event.kind())) {
            // missed Events aren't a Problem
            continue;
          }

          if (INDEX_FILE.equals(event.context()
                                     .toString())) {
            // no op (Index was refreshes - prevents re-indexing loop)
            continue;
          }

          if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
            // register new directory
            final Path newDir = Paths.get(key.watchable()
                                             .toString(),
                    event.context()
                         .toString());

            if (Files.isDirectory(newDir)) {
              try {
                registerDirs(newDir);
              } catch (final IOException ioe) {
                logger.error("Error while registering new sub-directories", ioe);
              }
            }
          }

          // mark Repo for Re-Indexing
          stale.set(true);
        }

        // reset Key
        final boolean valid = key.reset() && validate(key);

        if (!valid) {
          // Folder is not longer observable (deleted)
          final String dir = key.watchable()
                                .toString();
          watchKeys.remove(dir);
          logger.debug("no longer watching " + dir);

          if (watchKeys.isEmpty()) {
            // no Folder to observe
            logger.debug("dir watcher stopped");
            break;
          }
        }
      }
    }
  }


  /**
   * Activates the Repository. OSGi Declarative Service Methode annotated with {@link Activate}.
   *
   * @param config Configuration of the Respository (injected by OSGi Framework)
   */
  @Activate
  public void activate(final Config config) {
    logger.info("config:" + "\n\trepoDir = " + config.dir() + "\n\trepoName = " + config.name());

    final String root = config.dir();

    if (root == null || root.trim()
                            .isEmpty()) {
      throw new IllegalStateException("config: Repository directory (repoDir) is not configured.");
    }



    repoRoot = Paths.get(root);

    indexerConfig.put(ResourceIndexer.COMPRESSED, Boolean.toString(false));
    indexerConfig.put(ResourceIndexer.PRETTY, Boolean.toString(true));
    indexerConfig.put(ResourceIndexer.REPOSITORY_NAME, "Bundle Repository");
    indexerConfig.put(ResourceIndexer.ROOT_URL, root);
    indexerConfig.put(ResourceIndexer.URL_TEMPLATE,
            "http://localhost:8080/repo/obr/" + config.dir() + "/%p%f");

    try {
      // Create directory structure (if neccesary)
      Files.createDirectories(repoRoot);

      // Instantiate WatchService
      watchService = FileSystems.getDefault()
                                .newWatchService();

      // Observe Repo-Directory (and Sub-directory)
      registerDirs(repoRoot);

      // start Watcher-Thread
      dirWatcher = new DirWatcher();
      new Thread(dirWatcher, "DirWatcherThread").start();

      // start Indexer-Thread (after 0.5s, every 2s, when DirWatcher registeres changes)
      indexerExecutor = Executors.newSingleThreadScheduledExecutor()
                                 .scheduleWithFixedDelay(() -> {
                                   if (stale.getAndSet(false)) {
                                     updateIndex();
                                   }
                                 }, 500, 2000, TimeUnit.MILLISECONDS);
    } catch (final IOException ioe) {
      logger.error("Error while starting DirWatcherThread", ioe);
      throw new IllegalStateException(ioe);
    }
  }


  /**
   * Deactivates the Repository. OSGi Declarative Service Method annotated with {@link Deactivate}.
   */
  @Deactivate
  public void deactivate() {
    if (indexerExecutor != null) {
      indexerExecutor.cancel(false);
      logger.debug("indexer executor stopped");
    }

    if (dirWatcher != null) {
      dirWatcher.stop = true;
      logger.debug("dir watcher thread stopped");
    }
  }


  /**
   * Removes a Bundle from Repository.
   *
   * @param path relative Path of Bundle in Repository
   * @return <code>true</code> if Bundle is found and removed; else <code>false</code>
   * @throws BundleRepositoryException Error deleting the bundle file
   */
  public boolean deleteBundle(final String path)
          throws BundleRepositoryException {
    final Path bundle = repoRoot.resolve(path);
    boolean deleted = false;

    try {
      if (Files.isRegularFile(bundle)) {
        // remove Bundle-File
        deleted = Files.deleteIfExists(bundle);
      }
    } catch (final IOException ioe) {
      throw new BundleRepositoryException("Error deleting the bundle file " + bundle, ioe);
    }

    return deleted;
  }


  @Reference
  public void bindLogService(final LogService log) {
    this.log = log;
    logger.debug("OSGi log service bound: " + log);
  }


  private boolean validate(final WatchKey key) {
    final String dir = key.watchable()
                          .toString();

    return Files.isDirectory(Paths.get(dir));
  }
}
