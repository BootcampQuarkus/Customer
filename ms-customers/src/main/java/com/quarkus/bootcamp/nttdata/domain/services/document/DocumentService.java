package com.quarkus.bootcamp.nttdata.domain.services.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

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

  @Override
  public List<Document> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            Document document = mapper.toEntity(p);
            document.setDocumentType(dtMapper.toEntity(p.getDocumentTypeD()));
            return document;
          })
          .toList();
  }

  @Override
  public Document getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            Document document = mapper.toEntity(p);
            document.setDocumentType(dtMapper.toEntity(p.getDocumentTypeD()));
            return document;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public Document create(Document document) {
    DocumentD documentD = mapper.toDto(document);
    try {
      documentD.setDocumentTypeD(dtRepository.findByIdOptional(document.getDocumentTypeId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (Exception e) {
      throw new NotFoundException("Document Type not found");
    }
    document = mapper.toEntity(repository.save(documentD));
    document.setDocumentType(dtMapper.toEntity(documentD.getDocumentTypeD()));
    return document;
  }

  @Override
  public Document update(Long id, Document document) {
    DocumentD documentD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    documentD.setSerial(document.getSerial());
    try {
      documentD.setDocumentTypeD(dtRepository.findByIdOptional(document.getDocumentTypeId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (NotFoundException e) {
      throw new NotFoundException("Document Type not found");
    }
    document = mapper.toEntity(repository.save(documentD));
    document.setDocumentType(dtMapper.toEntity(documentD.getDocumentTypeD()));
    document.setDocumentTypeId(document.getDocumentType().getId());
    return document;
  }

  @Override
  public Document delete(Long id) {
    DocumentD documentD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    Document document = mapper.toEntity(repository.softDelete(documentD));
    document.setDocumentType(dtMapper.toEntity(documentD.getDocumentTypeD()));
    document.setDocumentTypeId(document.getDocumentType().getId());
    return document;
  }
}
