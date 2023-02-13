package com.quarkus.bootcamp.nttdata.domain.entity.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DocumentType {
  protected Long id;
  protected String description;
  protected List<Document> documents;
}
