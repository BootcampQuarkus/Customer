package com.quarkus.bootcamp.nttdata.domain.mapper.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BodyCorporateMapper implements IMapper<BodyCorporate, BodyCorporateD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param bodyCorporate Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public BodyCorporateD toDto(BodyCorporate bodyCorporate) throws NullPointerException {
    BodyCorporateD bodyCorporateD = new BodyCorporateD();
    bodyCorporateD.setName(bodyCorporate.getName());
    return bodyCorporateD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param bodyCorporateD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public BodyCorporate toEntity(BodyCorporateD bodyCorporateD) throws NullPointerException {
    BodyCorporate bodyCorporate = new BodyCorporate();
    bodyCorporate.setId(bodyCorporateD.id);
    bodyCorporate.setName(bodyCorporateD.getName());
    bodyCorporate.setDocumentId(bodyCorporateD.getDocumentD().id);
    bodyCorporate.setAddressId(bodyCorporateD.getAddressD().id);
    return bodyCorporate;
  }
}
