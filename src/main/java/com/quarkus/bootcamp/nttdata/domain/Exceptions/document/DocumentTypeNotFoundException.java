package com.quarkus.bootcamp.nttdata.domain.Exceptions.document;

public class DocumentTypeNotFoundException extends Exception {
  public DocumentTypeNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
