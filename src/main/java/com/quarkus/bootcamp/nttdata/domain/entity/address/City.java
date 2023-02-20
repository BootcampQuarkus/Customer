package com.quarkus.bootcamp.nttdata.domain.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad de logica de negocio para las ciudades.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class City {
  protected Long id;
  /**
   * Nombre de la ciudad.
   */
  protected String name;
  /**
   * Estado al que pertenece la ciudad.
   */
  protected State states;
  /**
   * Identificador del estado al que pertenece la ciudad.
   */
  protected Long stateId;
  /**
   * Colección de direcciones
   */
  protected List<Address> addresses;
}
