package com.quarkus.bootcamp.nttdata.domain.Exceptions.address;

public class StateNotFoundException extends Exception {
  public StateNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
