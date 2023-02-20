package com.quarkus.bootcamp.nttdata.domain.mapper.document;

import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class DocumentMapperTest {
  @Inject
  DocumentMapper mapper;

  @Test
  void toEntityNull() {
    Document document = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(document));
  }

  @Test
  void toDtoNull() {
    DocumentD documentD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(documentD));
  }

  @Test
  void toEntityReturnEntity() {
    Document document = new Document();
    Assertions.assertInstanceOf(DocumentD.class, mapper.toEntity(document));
  }

  @Test
  void toDtoReturnDto() {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    DocumentD documentD = new DocumentD();
    documentD.setDocumentTypeD(documentTypeD);
    Assertions.assertInstanceOf(Document.class, mapper.toDto(documentD));
  }

  @Test
  void toEntityVoid() {
    Document document = new Document();
    DocumentD expected = new DocumentD();
    DocumentD actual = mapper.toEntity(document);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoVoid() {
    DocumentD documentD = new DocumentD();
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(documentD));
  }

  @Test
  void toEntityValid() {
    Document document = new Document();
    document.setSerial("123456789");
    DocumentD expected = new DocumentD();
    expected.setSerial("123456789");
    DocumentD actual = mapper.toEntity(document);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoValid() {
    DocumentTypeD documentTypeD = new DocumentTypeD();
    documentTypeD.id = 1L;
    DocumentD documentD = new DocumentD();
    documentD.id = 2L;
    documentD.setSerial("123456789");
    documentD.setDocumentTypeD(documentTypeD);

    Document expected = new Document();
    expected.setId(2L);
    expected.setSerial("123456789");
    expected.setDocumentTypeId(1L);
    Document actual = mapper.toDto(documentD);
    Assertions.assertEquals(expected, actual);
  }
}