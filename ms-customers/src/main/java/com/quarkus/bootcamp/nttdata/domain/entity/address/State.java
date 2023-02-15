package com.quarkus.bootcamp.nttdata.domain.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad de logica de negocio para los estados/departamentos.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class State {
  protected Long id;
  /**
   * Nombre del departamento.
   */
  protected String name;
  /**
   * Lista de ciudades que están dentro del estado/departamento.
   */
  protected List<City> cities;
  /**
   * Lista de direcciones que se encuentran dentro de dicho estado/departamento.
   */
  protected List<Address> addresses;
}
