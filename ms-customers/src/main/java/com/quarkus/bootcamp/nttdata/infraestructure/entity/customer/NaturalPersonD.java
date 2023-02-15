package com.quarkus.bootcamp.nttdata.infraestructure.entity.customer;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de BD para las personas naturales.
 * Terminologia -> https://files.consumerfinance.gov/f/201510_cfpb_spanish-style-guide-glossary.pdf
 */
@Entity
@Table(name = "naturalperson")
@Data
@NoArgsConstructor
public class NaturalPersonD extends PanacheEntity {
  /**
   * Nombre de la persona natural.
   */
  protected String name;
  /**
   * Apellido de la persona natural.
   */
  protected String lastName;
  ///-----------------------------
  /**
   * Documento de la persona natural.
   * DNI, Carnet de extranjeria, etc
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "documentId")
  protected DocumentD documentD;
  /**
   * Dirección de la persona natural.
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
