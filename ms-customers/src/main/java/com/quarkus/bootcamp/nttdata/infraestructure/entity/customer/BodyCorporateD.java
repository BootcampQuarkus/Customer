package com.quarkus.bootcamp.nttdata.infraestructure.entity.customer;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de BD para las personas juridicas.
 * Terminologia -> https://documents1.worldbank.org/curated/en/541831468326979631/pdf/322800PUB00PUB0d0bank0glossary01996.pdf
 */
@Entity
@Table(name = "bodycorporate")
@Data
@NoArgsConstructor
public class BodyCorporateD extends PanacheEntity {
  /**
   * Nombre de la persona juridica (empresa).
   */
  protected String name;
  /**
   * Documento de la persona juridica.
   * RUC
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "documentId")
  protected DocumentD documentD;
  /**
   * Dirección de la persona juridica/dirección fiscal.
   */
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
