package com.quarkus.bootcamp.nttdata.domain.entity.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de logica de negocio para los clientes.
 *
 * @author pdiaz
 */

@Data
@NoArgsConstructor
public class Customer {
  protected Long id;
  /**
   * Documento del cliente.
   * Todos los clientes (persona natural o juridica) deben tener un documento.
   */
  protected Document document;
  /**
   * Dirección del cliente.
   */
  protected Address address;
  /**
   * Identificador del documento.
   */
  protected Long documentId;
  /**
   * Identificador de la dirección.
   */
  protected Long addressId;
}
