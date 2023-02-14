package com.quarkus.bootcamp.nttdata.infraestructure.entity.document;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
public class DocumentD extends PanacheEntity {
  /**
   * Numero de documento
   */
  protected String serial;
  /**
   * Tipo de documento que pertenece.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "documentTypeId")
  protected DocumentTypeD documentTypeD;
  @OneToOne(mappedBy = "documentD", fetch = FetchType.LAZY)
  protected NaturalPersonD naturalPersonD;
  @OneToOne(mappedBy = "documentD", fetch = FetchType.LAZY)
  protected BodyCorporateD bodyCorporateD;
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
