package com.quarkus.bootcamp.nttdata.domain.Exceptions.customer;

public class NaturalPersonNotFoundException extends Exception {
  public NaturalPersonNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
