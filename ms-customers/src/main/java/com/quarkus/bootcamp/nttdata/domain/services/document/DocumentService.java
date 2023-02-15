package com.quarkus.bootcamp.nttdata.domain.services.document;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentTypeNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Clase de servicio con las principales funciones para los resources.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class DocumentService implements IService<Document, Document> {
  @Inject
  DocumentRepository repository;
  @Inject
  DocumentTypeRepository dtRepository;
  @Inject
  DocumentMapper mapper;
  @Inject
  DocumentTypeMapper dtMapper;

  /**
   * Retorna todos los documentos no eliminadas.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de documentos no eliminados.
   */
  @Override
  public List<Document> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(this::documentWithDocumentType)
          .toList();
  }

  /**
   * Retorna el documento si no está eliminado.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return documento no eliminado.
   * @throws DocumentNotFoundException
   */
  @Override
  public Document getById(Long id) throws DocumentNotFoundException {
    return repository.findByIdOptional(id)
          .map(this::documentWithDocumentType)
          .orElseThrow(() -> new DocumentNotFoundException("Document not found."));
  }

  /**
   * Guarda un documento y retorna el documento guardado.
   *
   * @param document El elemento a crear.
   * @return dirección creada.
   * @throws DocumentTypeNotFoundException
   */
  @Override
  public Document create(Document document) throws DocumentTypeNotFoundException {
    DocumentD documentD = mapper.toEntity(document);
    documentD.setDocumentTypeD(dtRepository.findByIdOptional(document.getDocumentTypeId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentTypeNotFoundException("Document type not found.")));
    document = mapper.toDto(repository.save(documentD));
    document.setDocumentType(dtMapper.toDto(documentD.getDocumentTypeD()));
    return document;
  }

  /**
   * Actualiza los datos de un documento previamente guardada.
   *
   * @param id Identificador del elemento a editar.
   * @param document  Elemento con los datos para guardar.
   * @return documento actualizado.
   * @throws DocumentNotFoundException
   * @throws DocumentTypeNotFoundException
   */
  @Override
  public Document update(Long id, Document document) throws DocumentNotFoundException, DocumentTypeNotFoundException {
    DocumentD documentD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentNotFoundException("Document not found."));
    documentD.setSerial(document.getSerial());
    documentD.setDocumentTypeD(dtRepository.findByIdOptional(document.getDocumentTypeId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentTypeNotFoundException("Document type not found.")));
    return documentWithDocumentTypeAndId(mapper.toDto(repository.softDelete(documentD)), documentD);
  }

  /**
   * Elimina (softdelete) un documento de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return documento eliminado.
   * @throws DocumentNotFoundException
   */
  @Override
  public Document delete(Long id) throws DocumentNotFoundException {
    DocumentD documentD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentNotFoundException("Documento not found"));
    return documentWithDocumentTypeAndId(mapper.toDto(repository.softDelete(documentD)), documentD);
  }

  public Document documentWithDocumentTypeAndId(Document document, DocumentD documentD) {
    document.setDocumentType(dtMapper.toDto(documentD.getDocumentTypeD()));
    document.setDocumentTypeId(document.getDocumentType().getId());
    return document;
  }

  public Document documentWithDocumentType(DocumentD documentD) {
    Document document = mapper.toDto(documentD);
    document.setDocumentType(dtMapper.toDto(documentD.getDocumentTypeD()));
    return document;
  }
}
