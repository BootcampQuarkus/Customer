package com.quarkus.bootcamp.nttdata.infraestructure.entity.address;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
public class StateD extends PanacheEntity {
  /**
   * Nombre del departamento.
   */
  protected String name;

  ///-----------------------------
  /**
   * Collección de ciudades dentro de dicho estado.
   * El campo para hacer el mapping es stateD.
   */
  @OneToMany(mappedBy = "stateD", orphanRemoval = true)
  protected List<CityD> citiesD;
  /**
   * Collección de direcciones dentro de dicho estado.
   * El campo para hacer el mapping es cardTypeD.
   */
  @OneToMany(mappedBy = "stateD", orphanRemoval = true)
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
