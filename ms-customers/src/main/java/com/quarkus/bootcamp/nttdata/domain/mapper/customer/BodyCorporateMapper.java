package com.quarkus.bootcamp.nttdata.domain.mapper.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Clase para transformaciones entre BodyCorporate y BodyCorporateD
 *
 * @author pdiaz
 */
@ApplicationScoped
public class BodyCorporateMapper implements IMapper<BodyCorporate, BodyCorporateD> {
  /**
   * Transforma el objeto de BodyCorporate a BodyCorporateD.
   *
   * @param bodyCorporate Objeto de la clase BodyCorporate que se desea transformar.
   * @return Objeto de la clase BodyCorporateD.
   * @throws NullPointerException
   */
  @Override
  public BodyCorporateD toEntity(BodyCorporate bodyCorporate) throws NullPointerException {
    BodyCorporateD bodyCorporateD = new BodyCorporateD();
    bodyCorporateD.setName(bodyCorporate.getName());
    return bodyCorporateD;
  }

  /**
   * Transforma el objeto de BodyCorporateD a BodyCorporate.
   *
   * @param bodyCorporateD Objeto de la clase BodyCorporateD que se desea transformar.
   * @return Objeto de la clase BodyCorporate.
   * @throws NullPointerException
   */
  @Override
  public BodyCorporate toDto(BodyCorporateD bodyCorporateD) throws NullPointerException {
    BodyCorporate bodyCorporate = new BodyCorporate();
    bodyCorporate.setId(bodyCorporateD.id);
    bodyCorporate.setName(bodyCorporateD.getName());
    bodyCorporate.setDocumentId(bodyCorporateD.getDocumentD().id);
    bodyCorporate.setAddressId(bodyCorporateD.getAddressD().id);
    return bodyCorporate;
  }
}
