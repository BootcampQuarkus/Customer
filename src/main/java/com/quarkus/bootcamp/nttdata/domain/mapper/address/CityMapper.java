package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Clase para transformaciones entre City y CityD
 *
 * @author pdiaz
 */
@ApplicationScoped
public class CityMapper implements IMapper<City, CityD> {
  /**
   * Transforma el objeto de City a CityD.
   *
   * @param city Objeto de la clase City que se desea transformar.
   * @return Objeto de la clase CityD.
   * @throws NullPointerException
   */
  @Override
  public CityD toEntity(City city) throws NullPointerException {
    CityD cityD = new CityD();
    cityD.setName(city.getName());
    return cityD;
  }

  /**
   * Transforma el objeto de CityD a City.
   *
   * @param cityD Objeto de la clase CityD que se desea transformar.
   * @return Objeto de la clase City.
   * @throws NullPointerException
   */
  @Override
  public City toDto(CityD cityD) throws NullPointerException {
    City city = new City();
    city.setId(cityD.id);
    city.setName(cityD.getName());
    city.setStateId(cityD.getStateD().id);
    return city;
  }
}
