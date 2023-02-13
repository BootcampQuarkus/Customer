package com.quarkus.bootcamp.nttdata.domain.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class City {
  protected Long id;
  /**
   * Nombre de la ciudad.
   */
  protected String name;
  /**
   * Collección de estados.
   */
  protected State states;
  /**
   *
   */
  protected Long stateId;
  /**
   *
   */
  protected List<Address> addresses;
}
