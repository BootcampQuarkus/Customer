package com.quarkus.bootcamp.nttdata.domain.Exceptions.customer;

public class BodyCorporateNotFoundException extends Exception {
  public BodyCorporateNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
