package com.quarkus.bootcamp.nttdata.domain.services.document;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentTypeNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.document.DocumentType;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
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
public class DocumentTypeService implements IService<DocumentType, DocumentType> {
  @Inject
  DocumentTypeRepository repository;
  @Inject
  DocumentTypeMapper mapper;
  @Inject
  DocumentMapper dMapper;

  /**
   * Retorna todos los tipos de documentos no eliminados.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de tipos de documentos no eliminados.
   */
  @Override
  public List<DocumentType> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(this::documentTypeWithDocuments)
          .toList();
  }

  /**
   * Retorna el tipo de documento si no está eliminado.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return tipo de documento no eliminado.
   * @throws DocumentTypeNotFoundException
   */
  @Override
  public DocumentType getById(Long id) throws DocumentTypeNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(this::documentTypeWithDocuments)
          .orElseThrow(() -> new DocumentTypeNotFoundException("Document type not found."));
  }

  /**
   * Guarda un tipo de documento y retorna el tipo de documento guardado.
   *
   * @param documentType El elemento a crear.
   * @return tipo de documento creado.
   */
  @Override
  public DocumentType create(DocumentType documentType) {
    return mapper.toDto(repository.save(mapper.toEntity(documentType)));
  }

  /**
   * Actualiza los datos de un tipo de documento previamente guardado.
   *
   * @param id Identificador del elemento a editar.
   * @param documentType  Elemento con los datos para guardar.
   * @return tipo de documento actualizado.
   * @throws DocumentTypeNotFoundException
   */
  @Override
  public DocumentType update(Long id, DocumentType documentType) throws DocumentTypeNotFoundException {
    DocumentTypeD documentTypeD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentTypeNotFoundException("Document type not found."));
    documentTypeD.setDescription(documentType.getDescription());
    documentType = mapper.toDto(repository.save(documentTypeD));
    documentType.setDocuments(documentTypeD.getDocumentDs()
          .stream()
          .filter(p -> p.getDeletedAt() == null)
          .map(p -> dMapper.toDto(p))
          .toList());
    return documentType;
  }

  /**
   * Elimina (softdelete) un tipo de documento de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return tipo de document eliminado.
   * @throws DocumentTypeNotFoundException
   */
  @Override
  public DocumentType delete(Long id) throws DocumentTypeNotFoundException {
    DocumentTypeD documentTypeD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentTypeNotFoundException("Document type not found."));
    return mapper.toDto(repository.softDelete(documentTypeD));
  }

  public DocumentType documentTypeWithDocuments(DocumentTypeD documentTyped) {
    DocumentType documentType = mapper.toDto(documentTyped);
    documentType.setDocuments(documentTyped.getDocumentDs()
          .stream()
          .filter(q -> q.getDeletedAt() == null)
          .map(dMapper::toDto)
          .toList());
    return documentType;
  }
}
