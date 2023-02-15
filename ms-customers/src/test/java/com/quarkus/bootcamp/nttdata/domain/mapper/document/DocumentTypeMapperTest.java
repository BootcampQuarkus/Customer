package com.quarkus.bootcamp.nttdata.domain.mapper.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.DocumentType;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class DocumentTypeMapperTest {
  @Inject
  DocumentTypeMapper mapper;

  @Test
  void toEntityNull() {
    DocumentType documentType = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(documentType));
  }

  @Test
  void toDtoNull() {
    DocumentTypeD documentTypeD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(documentTypeD));
  }

  @Test
  void toEntityReturnEntity() {
    DocumentType documentType = new DocumentType();
    Assertions.assertInstanceOf(DocumentTypeD.class, mapper.toEntity(documentType));
  }

  @Test
  void toDtoReturnDto() {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    Assertions.assertInstanceOf(DocumentType.class, mapper.toDto(documentTypeD));
  }

  @Test
  void toEntityVoid() {
    DocumentType documentType = new DocumentType();
    DocumentTypeD expected = new DocumentTypeD();
    DocumentTypeD actual = mapper.toEntity(documentType);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoVoid() {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    DocumentType expected = new DocumentType();
    DocumentType actual = mapper.toDto(documentTypeD);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toEntityValid() {
    DocumentType documentType = new DocumentType();
    documentType.setDescription("Tipo de documento.");
    DocumentTypeD expected = new DocumentTypeD();
    expected.setDescription("Tipo de documento.");
    DocumentTypeD actual = mapper.toEntity(documentType);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoValid() {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    documentTypeD.id = 1L;
    documentTypeD.setDescription("Tipo de documento.");
    DocumentType expected = new DocumentType();
    expected.setId(1L);
    expected.setDescription("Tipo de documento.");
    DocumentType actual = mapper.toDto(documentTypeD);
    Assertions.assertEquals(expected, actual);
  }
}