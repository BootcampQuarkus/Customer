package com.quarkus.bootcamp.nttdata.infraestructure.entity.address;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de BD para las direcciones de los clientes.
 *
 * @author pdiaz
 */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
public class AddressD extends PanacheEntity {
  /**
   * Detalle de la dirección
   * Ejm: Av. Arequipa xxx
   */
  protected String address;

  ///-----------------------------
  /**
   * Departamento.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stateId")
  protected StateD stateD;
  /**
   * Ciudad.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cityId")
  protected CityD cityD;
  /**
   * Relación OneToOne con Personas Naturales
   */
  @OneToOne(mappedBy = "addressD", fetch = FetchType.LAZY)
  protected NaturalPersonD naturalPersonD;
  /**
   * Relación OneToOne con Persona Juridica
   */
  @OneToOne(mappedBy = "addressD", fetch = FetchType.LAZY)
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
