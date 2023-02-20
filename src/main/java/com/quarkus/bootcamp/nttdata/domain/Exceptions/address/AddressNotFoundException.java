package com.quarkus.bootcamp.nttdata.domain.Exceptions.address;

public class AddressNotFoundException extends Exception {
  public AddressNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
