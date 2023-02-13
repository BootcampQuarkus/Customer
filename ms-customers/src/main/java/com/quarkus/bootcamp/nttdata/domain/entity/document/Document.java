package com.quarkus.bootcamp.nttdata.domain.entity.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Document {
  protected Long id;
  protected String serial;
  protected DocumentType documentType;
  protected Long documentTypeId;

}
