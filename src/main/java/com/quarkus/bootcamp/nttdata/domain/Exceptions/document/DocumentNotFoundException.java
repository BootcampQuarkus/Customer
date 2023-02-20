package com.quarkus.bootcamp.nttdata.domain.Exceptions.document;

public class DocumentNotFoundException extends Exception {
  public DocumentNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
