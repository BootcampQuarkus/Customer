package com.quarkus.bootcamp.nttdata.domain.entity.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BodyCorporate extends Customer {
  protected String name;
}
