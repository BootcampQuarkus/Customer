package com.quarkus.bootcamp.nttdata.domain.entity.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
  protected Long id;
  protected Document document;
  protected Address address;
  protected Long documentId;
  protected Long addressId;
}
