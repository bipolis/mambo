package org.bipolis.mambo.repo;

/**
 */
public class RepositoryAdditionResult {

  private final boolean sucess;

  private final String location;

  private final byte[] digest;

  public RepositoryAdditionResult(final boolean success,
          final String location,
          final byte[] digest) {
    sucess = success;
    this.digest = digest;
    this.location = location;
  }

  public boolean isSuccess() {
    return sucess;
  }

  public String location() {
    return location;
  }

  public byte[] digest() {
    return digest;
  }
}
