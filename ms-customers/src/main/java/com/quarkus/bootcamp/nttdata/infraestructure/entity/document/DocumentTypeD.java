package com.quarkus.bootcamp.nttdata.infraestructure.entity.document;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "documentType")
@Data
@NoArgsConstructor
public class DocumentTypeD extends PanacheEntity {
  /**
   * Descripción del documento
   * Ejm: DNI, RUC, etc
   */
  protected String description;
  ///-----------------------------
  /**
   * Collección de ciudades dentro de dicho estado.
   * El campo para hacer el mapping es stateD.
   */
  @OneToMany(mappedBy = "documentTypeD", orphanRemoval = true)
  protected List<DocumentD> documentDs;

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
