package com.quarkus.bootcamp.nttdata.domain.entity.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de logica de negocio para los clientes del tipo Persona Juridica.
 * Extiende de la entidad logica de negocio Customer.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class BodyCorporate extends Customer {
  /**
   * Nombre de la empresa/negocio.
   */
  protected String name;
}
