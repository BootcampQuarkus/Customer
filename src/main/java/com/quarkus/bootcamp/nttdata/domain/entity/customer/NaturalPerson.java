package com.quarkus.bootcamp.nttdata.domain.entity.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de logica de negocio para los clientes del tipo Persona natural.
 * Extiende de la entidad logica de negocio Customer.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class NaturalPerson extends Customer {
  /**
   * Nombre de pila.
   */
  protected String name;
  /**
   * Apellidos.
   */
  protected String lastName;
}
