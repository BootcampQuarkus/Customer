package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.AddressMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.AddressRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.CityRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Clase de servicio con las principales funciones para los resources.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class AddressService implements IService<Address, Address> {
  @Inject
  AddressRepository repository;
  @Inject
  StateRepository sRepository;
  @Inject
  CityRepository cRepository;
  @Inject
  AddressMapper mapper;
  @Inject
  StateMapper sMapper;
  @Inject
  CityMapper cMapper;

  /**
   * Retorna todas las direcciones no eliminadas.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de direcciones no eliminadas.
   */
  @Override
  public List<Address> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(this::addressWithStateCityAndIds)
          .toList();
  }

  /**
   * Retorna la dirección si no está eliminada.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return dirección no eliminada
   * @throws AddressNotFoundException
   */
  @Override
  public Address getById(Long id) throws AddressNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(this::addressWithStateCityAndIds)
          .orElseThrow(() -> new AddressNotFoundException("Address not found."));
  }

  /**
   * Guarda una dirección y retorna la dirección guardada.
   *
   * @param address El elemento a crear.
   * @return dirección creada.
   * @throws StateNotFoundException
   * @throws CityNotFoundException
   */
  @Override
  public Address create(Address address) throws StateNotFoundException, CityNotFoundException {
    AddressD addressD = addressDWithStateDAndCityD(address, mapper.toEntity(address));
    return this.addressWithStateAndCity(repository.save(addressD));
  }

  /**
   * Actualiza los datos de una dirección previamente guardada.
   *
   * @param id Identificador del elemento a editar.
   * @param address  Elemento con los datos para guardar.
   * @return dirección actualizada.
   * @throws StateNotFoundException
   * @throws CityNotFoundException
   * @throws AddressNotFoundException
   */
  @Override
  public Address update(Long id, Address address) throws StateNotFoundException, CityNotFoundException, AddressNotFoundException {
    AddressD addressD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new AddressNotFoundException("Address not found."));
    addressD = addressDWithStateDAndCityD(address, addressD);
    addressD.setAddress(address.getAddress());
    return addressWithStateAndCity(repository.save(addressD));
  }

  /**
   * Elimina (softdelete) una dirección de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return dirección eliminada.
   * @throws AddressNotFoundException
   */
  @Override
  public Address delete(Long id) throws AddressNotFoundException {
    AddressD addressD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new AddressNotFoundException("Address not found."));
    return addressWithStateAndCity(repository.softDelete(addressD));
  }

  /**
   * Se agrega a un Address el State y City que se obtiene del AddressD.
   *
   * @param address
   * @param addressD
   * @return
   * @throws CityNotFoundException
   * @throws StateNotFoundException
   */
  public AddressD addressDWithStateDAndCityD(Address address, AddressD addressD) throws CityNotFoundException, StateNotFoundException {
    addressD.setCityD(cRepository.findByIdOptional(address.getCityId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new CityNotFoundException("City not found.")));
    addressD.setStateD(sRepository.findByIdOptional(address.getStateId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new StateNotFoundException("State not found")));
    return addressD;
  }

  /**
   * Se Transforma un AddressD a Address con su State y City.
   *
   * @param addressD Objeto del tipo AddressD.
   * @return Objeto Address con los valores del parámetro transformados a entidades de lógica de negocio.
   */
  public Address addressWithStateAndCity(AddressD addressD) {
    Address address = mapper.toDto(addressD);
    address.setState(sMapper.toDto(addressD.getStateD()));
    address.setCity(cMapper.toDto(addressD.getCityD()));
    return address;
  }

  /**
   * Se Transforma un AddressD a Address con su State, City e identificadores de cada uno.
   *
   * @param addressD Objeto del tipo AddressD.
   * @return Objeto Address con los valores del parámetro transformados a entidades de lógica de negocio.
   */
  public Address addressWithStateCityAndIds(AddressD addressD) {
    Address address = this.addressWithStateAndCity(addressD);
    address.setStateId(address.getState().getId());
    address.setCityId(address.getCity().getId());
    return address;
  }
}
