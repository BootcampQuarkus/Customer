package com.quarkus.bootcamp.nttdata.domain.entity.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad de logica de negocio para los tipos de documentos.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class DocumentType {
  protected Long id;
  /**
   * Nombre del documento.
   * Ejm: DNI, RUC, CARNET DE EXTRANJERIA, ETC.
   */
  protected String description;
  /**
   * Lista de documentos del tipo.
   */
  protected List<Document> documents;
}
