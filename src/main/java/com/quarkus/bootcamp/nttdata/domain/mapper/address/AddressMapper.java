package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Clase para transformaciones entre Address y AddressD
 *
 * @author pdiaz
 */
@ApplicationScoped
public class AddressMapper implements IMapper<Address, AddressD> {
  /**
   * Transforma el objeto de Address a AddressD.
   *
   * @param address Objeto de la clase Address que se desea transformar.
   * @return Objeto de la clase AddressD.
   * @throws NullPointerException
   */
  @Override
  public AddressD toEntity(Address address) throws NullPointerException {
    AddressD addressD = new AddressD();
    addressD.setAddress(address.getAddress());
    return addressD;
  }

  /**
   * Transforma el objeto de AddressD a Address.
   *
   * @param addressD Objeto de la clase AddressD que se desea transformar.
   * @return Objeto de la clase Address.
   * @throws NullPointerException
   */
  @Override
  public Address toDto(AddressD addressD) throws NullPointerException {
    Address address = new Address();
    address.setId(addressD.id);
    address.setAddress(addressD.getAddress());
    address.setStateId(addressD.getStateD().id);
    address.setCityId(addressD.getCityD().id);
    return address;
  }
}
