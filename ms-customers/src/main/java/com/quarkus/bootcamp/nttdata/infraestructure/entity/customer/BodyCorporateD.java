package com.quarkus.bootcamp.nttdata.infraestructure.entity.customer;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://documents1.worldbank.org/curated/en/541831468326979631/pdf/322800PUB00PUB0d0bank0glossary01996.pdf
 */
@Entity
@Table(name = "bodycorporate")
@Data
@NoArgsConstructor
public class BodyCorporateD extends PanacheEntity {
  protected String name;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "documentId")
  protected DocumentD documentD;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "addressId")
  protected AddressD addressD;
  ///-----------------------------
  /**
   * Fecha en la que se creó.
   */
  @Column(nullable = true)
  protected String createdAt = null;
  /**
   * Fecha de la ultima actualización.
   */
  @Column(nullable = true)
  protected String updatedAt = null;
  /**
   * Fecha en la que se eliminó.
   */
  @Column(nullable = true)
  protected String deletedAt = null;
}
