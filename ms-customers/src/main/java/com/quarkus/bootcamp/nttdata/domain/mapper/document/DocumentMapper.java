package com.quarkus.bootcamp.nttdata.domain.mapper.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentMapper implements IMapper<Document, DocumentD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param document Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public DocumentD toDto(Document document) throws NullPointerException {
    DocumentD documentD = new DocumentD();
    documentD.setSerial(document.getSerial());
    return documentD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param documentD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public Document toEntity(DocumentD documentD) throws NullPointerException {
    Document document = new Document();
    document.setId(documentD.id);
    document.setSerial(documentD.getSerial());
    document.setDocumentTypeId(documentD.getDocumentTypeD().id);
    return document;
  }
}
