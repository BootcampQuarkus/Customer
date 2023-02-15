package com.quarkus.bootcamp.nttdata.domain.entity.document;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de logica de negocio para los documentos.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class Document {
  protected Long id;
  /**
   * Serie del documento.
   * Ejm: 12345678 (DNI)
   * 12345678901 (RUC)
   */
  protected String serial;
  /**
   * Tipo de documento (DNI, RUC, etc...).
   */
  protected DocumentType documentType;
  /**
   * Identificador del tipo de documento.
   */
  protected Long documentTypeId;

}
