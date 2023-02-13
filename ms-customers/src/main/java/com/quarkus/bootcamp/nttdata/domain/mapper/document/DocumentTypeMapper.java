package com.quarkus.bootcamp.nttdata.domain.mapper.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.DocumentType;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentTypeMapper implements IMapper<DocumentType, DocumentTypeD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param documentType Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public DocumentTypeD toDto(DocumentType documentType) throws NullPointerException {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    documentTypeD.setDescription(documentType.getDescription());
    return documentTypeD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param documentTypeD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public DocumentType toEntity(DocumentTypeD documentTypeD) throws NullPointerException {
    DocumentType documentType = new DocumentType();
    documentType.setId(documentTypeD.id);
    documentType.setDescription(documentTypeD.getDescription());
    return documentType;
  }
}
