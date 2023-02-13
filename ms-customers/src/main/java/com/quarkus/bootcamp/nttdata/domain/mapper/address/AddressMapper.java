package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressMapper implements IMapper<Address, AddressD> {
  /**
   * Transforma el objeto de Card a CardD.
   *
   * @param address Objeto de la clase Card que se desea transformar.
   * @return Objeto de la clase CardD.
   * @throws NullPointerException
   */
  @Override
  public AddressD toDto(Address address) throws NullPointerException {
    AddressD addressD = new AddressD();
    addressD.setAddress(address.getAddress());
    return addressD;
  }

  /**
   * Transforma el objeto de CardD a Card.
   *
   * @param addressD Objeto de la clase CardD que se desea transformar.
   * @return Objeto de la clase Card.
   * @throws NullPointerException
   */
  @Override
  public Address toEntity(AddressD addressD) throws NullPointerException {
    Address address = new Address();
    address.setId(addressD.id);
    address.setAddress(addressD.getAddress());
    address.setStateId(addressD.getStateD().id);
    address.setCityId(addressD.getCityD().id);
    return address;
  }
}
