package com.quarkus.bootcamp.nttdata.domain.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {
  protected Long id;
  /**
   * Detalle de la direccion
   */
  protected String address;
  /**
   * Departamento
   */
  protected State state;
  /**
   * Identificador de departamento.
   */
  protected Long stateId;
  /**
   * Ciudad
   */
  protected City city;
  /**
   * Identificador de ciudad;
   */
  protected Long cityId;
}
