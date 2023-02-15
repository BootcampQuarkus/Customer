package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.AddressMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.AddressRepository;
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
  AddressMapper mapper;
  @Inject
  Util util;

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
          .map(util::addressWithStateCityAndIds)
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
          .map(util::addressWithStateCityAndIds)
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
    AddressD addressD = util.addressDWithStateDAndCityD(address, mapper.toEntity(address));
    return util.addressWithStateAndCity(repository.save(addressD));
  }

  /**
   * Actualiza los datos de una dirección previamente guardada.
   *
   * @param id      Identificador del elemento a editar.
   * @param address Elemento con los datos para guardar.
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
    addressD = util.addressDWithStateDAndCityD(address, addressD);
    addressD.setAddress(address.getAddress());
    return util.addressWithStateAndCity(repository.save(addressD));
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
    return util.addressWithStateAndCity(repository.softDelete(addressD));
  }
}