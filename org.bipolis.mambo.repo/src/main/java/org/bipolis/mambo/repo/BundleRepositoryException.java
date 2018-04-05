package org.bipolis.mambo.repo;

/**
 */
public class BundleRepositoryException extends RuntimeException {

  private static final long serialVersionUID = 1L;


  public BundleRepositoryException() {
    super();
  }


  public BundleRepositoryException(final String message, final Throwable cause) {
    super(message, cause);
  }


  public BundleRepositoryException(final String message) {
    super(message);
  }


  public BundleRepositoryException(final Throwable cause) {
    super(cause);
  }
}
