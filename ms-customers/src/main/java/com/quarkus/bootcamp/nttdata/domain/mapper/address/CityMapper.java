package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityMapper implements IMapper<City, CityD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param city Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public CityD toDto(City city) throws NullPointerException {
    CityD cityD = new CityD();
    cityD.setName(city.getName());
    return cityD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param cityD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public City toEntity(CityD cityD) throws NullPointerException {
    City city = new City();
    city.setId(cityD.id);
    city.setName(cityD.getName());
    city.setStateId(cityD.getStateD().id);
    return city;
  }
}
