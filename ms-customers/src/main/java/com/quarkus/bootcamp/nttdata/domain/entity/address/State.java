package com.quarkus.bootcamp.nttdata.domain.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class State {
  protected Long id;
  /**
   * Nombre del departamento.
   */
  protected String name;
  /**
   * Lista de ciudades.
   */
  protected List<City> cities;
  /**
   * Lista de direcciones
   */
  protected List<Address> addresses;
}
