package com.quarkus.bootcamp.nttdata.domain.Exceptions.address;

public class CityNotFoundException extends Exception {
  public CityNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
