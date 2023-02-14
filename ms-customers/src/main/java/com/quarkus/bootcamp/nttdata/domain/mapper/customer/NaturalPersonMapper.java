package com.quarkus.bootcamp.nttdata.domain.mapper.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NaturalPersonMapper implements IMapper<NaturalPerson, NaturalPersonD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param naturalPerson Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public NaturalPersonD toDto(NaturalPerson naturalPerson) throws NullPointerException {
    NaturalPersonD naturalPersonD = new NaturalPersonD();
    naturalPersonD.setName(naturalPerson.getName());
    naturalPersonD.setLastName(naturalPerson.getLastName());
    return naturalPersonD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param naturalPersonD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public NaturalPerson toEntity(NaturalPersonD naturalPersonD) throws NullPointerException {
    NaturalPerson naturalPerson = new NaturalPerson();
    naturalPerson.setId(naturalPersonD.id);
    naturalPerson.setName(naturalPersonD.getName());
    naturalPerson.setLastName(naturalPersonD.getLastName());
    naturalPerson.setDocumentId(naturalPersonD.getDocumentD().id);
    naturalPerson.setAddressId(naturalPersonD.getAddressD().id);
    return naturalPerson;
  }
}
