package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
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
public class CityService implements IService<City, City> {
  @Inject
  CityRepository repository;
  @Inject
  StateRepository sRepository;
  @Inject
  CityMapper mapper;
  @Inject
  Util util;

  /**
   * Retorna todas las ciudades no eliminadas.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de ciudades no eliminadas.
   */
  @Override
  public List<City> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::cityWithStateAndAddress)
          .toList();
  }

  /**
   * Retorna la ciudad si no está eliminada.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return ciudad no eliminada
   * @throws CityNotFoundException
   */
  @Override
  public City getById(Long id) throws CityNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::cityWithStateAndAddress)
          .orElseThrow(() -> new CityNotFoundException("City not found."));
  }

  /**
   * Guarda una ciudad y retorna la ciudad guardada.
   *
   * @param city El elemento a crear.
   * @return ciudad creada.
   * @throws StateNotFoundException
   */
  @Override
  public City create(City city) throws StateNotFoundException {
    CityD cityD = mapper.toEntity(city);
    cityD.setStateD(sRepository.findByIdOptional(city.getStateId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new StateNotFoundException("State not found.")));
    return mapper.toDto(repository.save(cityD));
  }

  /**
   * Actualiza los datos de una ciudad previamente guardada.
   *
   * @param id   Identificador del elemento a editar.
   * @param city Elemento con los datos para guardar.
   * @return ciudad actualizada.
   */
  @Override
  public City update(Long id, City city) throws CityNotFoundException, StateNotFoundException {
    CityD cityD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new CityNotFoundException("City not found."));
    cityD.setStateD(sRepository.findByIdOptional(city.getStateId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new StateNotFoundException("State not found.")));
    cityD.setName(city.getName());
    return mapper.toDto(repository.save(cityD));
  }

  /**
   * Elimina (softdelete) una ciudad de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return ciudad eliminada.
   */
  @Override
  public City delete(Long id) throws CityNotFoundException {
    CityD cityD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new CityNotFoundException("City not found."));
    return mapper.toDto(repository.softDelete(cityD));
  }
}