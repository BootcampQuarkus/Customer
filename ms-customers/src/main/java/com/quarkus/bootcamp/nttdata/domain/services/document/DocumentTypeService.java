package com.quarkus.bootcamp.nttdata.domain.services.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.DocumentType;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class DocumentTypeService implements IService<DocumentType, DocumentType> {
  @Inject
  DocumentTypeRepository repository;
  @Inject
  DocumentTypeMapper mapper;
  @Inject
  DocumentMapper dMapper;

  @Override
  public List<DocumentType> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            DocumentType documentType = mapper.toEntity(p);
            documentType.setDocuments(p.getDocumentDs()
                  .stream()
                  .filter(q -> q.getDeletedAt() == null)
                  .map(q -> dMapper.toEntity(q))
                  .toList());
            return documentType;
          })
          .toList();
  }

  @Override
  public DocumentType getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            DocumentType documentType = mapper.toEntity(p);
            documentType.setDocuments(p.getDocumentDs()
                  .stream()
                  .filter(q -> q.getDeletedAt() == null)
                  .map(q -> dMapper.toEntity(q))
                  .toList());
            return documentType;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public DocumentType create(DocumentType documentType) {
    return mapper.toEntity(repository.save(mapper.toDto(documentType)));
  }

  @Override
  public DocumentType update(Long id, DocumentType documentType) {
    DocumentTypeD documentTypeD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    documentTypeD.setDescription(documentType.getDescription());
    documentType = mapper.toEntity(repository.save(documentTypeD));
    documentType.setDocuments(documentTypeD.getDocumentDs()
          .stream()
          .filter(p -> p.getDeletedAt() == null)
          .map(p -> dMapper.toEntity(p))
          .toList());
    return documentType;
  }

  @Override
  public DocumentType delete(Long id) {
    DocumentTypeD documentTypeD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(documentTypeD));
  }
}
