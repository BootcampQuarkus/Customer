package com.quarkus.bootcamp.nttdata.infraestructure.entity.address;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "city")
@Data
@NoArgsConstructor
public class CityD extends PanacheEntity {
  /**
   * Nombre de la ciudad.
   */
  protected String name;

  ///-----------------------------
  /**
   * Departamento.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stateId")
  protected StateD stateD;

  ///-----------------------------
  /**
   * Collección de direcciones dentro de dicha ciudad.
   * El campo para hacer el mapping es cardTypeD.
   */
  @OneToMany(mappedBy = "cityD", orphanRemoval = true)
  protected List<AddressD> addressDs;

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
