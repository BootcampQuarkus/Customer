package com.quarkus.bootcamp.nttdata.domain.mapper.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Clase para transformaciones entre NaturalPerson y NaturalPersonD
 *
 * @author pdiaz
 */
@ApplicationScoped
public class NaturalPersonMapper implements IMapper<NaturalPerson, NaturalPersonD> {
  /**
   * Transforma el objeto de NaturalPerson a NaturalPersonD.
   *
   * @param naturalPerson Objeto de la clase NaturalPerson que se desea transformar.
   * @return Objeto de la clase NaturalPersonD.
   * @throws NullPointerException
   */
  @Override
  public NaturalPersonD toEntity(NaturalPerson naturalPerson) throws NullPointerException {
    NaturalPersonD naturalPersonD = new NaturalPersonD();
    naturalPersonD.setName(naturalPerson.getName());
    naturalPersonD.setLastName(naturalPerson.getLastName());
    return naturalPersonD;
  }

  /**
   * Transforma el objeto de NaturalPersonD a NaturalPerson.
   *
   * @param naturalPersonD Objeto de la clase NaturalPersonD que se desea transformar.
   * @return Objeto de la clase NaturalPerson.
   * @throws NullPointerException
   */
  @Override
  public NaturalPerson toDto(NaturalPersonD naturalPersonD) throws NullPointerException {
    NaturalPerson naturalPerson = new NaturalPerson();
    naturalPerson.setId(naturalPersonD.id);
    naturalPerson.setName(naturalPersonD.getName());
    naturalPerson.setLastName(naturalPersonD.getLastName());
    naturalPerson.setDocumentId(naturalPersonD.getDocumentD().id);
    naturalPerson.setAddressId(naturalPersonD.getAddressD().id);
    return naturalPerson;
  }
}
