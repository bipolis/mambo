package org.bipolis.mambo.jaxrs.openapi.api;

public class MergeException extends Exception {

  private static final long serialVersionUID = 1L;

  public <S> MergeException(S base, S merge, Throwable throwable) {
    super("Could not merge: base: " + base + " merge: " + merge, throwable);
  }
}
