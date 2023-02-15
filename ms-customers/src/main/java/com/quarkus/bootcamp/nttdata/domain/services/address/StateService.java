package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
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
public class StateService implements IService<State, State> {
  @Inject
  StateRepository repository;
  @Inject
  StateMapper mapper;
  @Inject
  Util util;

  /**
   * Retorna todos los states no eliminados.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de States no eliminadas.
   */
  @Override
  public List<State> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::stateWithCitiesAndAddress)
          .toList();
  }

  /**
   * Retorna el state si no esta eliminado.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return State no eliminada
   * @throws StateNotFoundException
   */
  @Override
  public State getById(Long id) throws StateNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::stateWithCitiesAndAddress)
          .orElseThrow(() -> new StateNotFoundException("State not found."));
  }

  /**
   * Guarda un State y retorna el State guardada.
   *
   * @param state El elemento a crear.
   * @return State creada.
   */
  @Override
  public State create(State state) {
    return mapper.toDto(repository.save(mapper.toEntity(state)));
  }

  /**
   * Actualiza los datos de un State previamente guardada.
   *
   * @param id    Identificador del elemento a editar.
   * @param state Elemento con los datos para guardar.
   * @return State actualizado.
   * @throws StateNotFoundException
   */
  @Override
  public State update(Long id, State state) throws StateNotFoundException {
    StateD stateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new StateNotFoundException("State not found."));
    stateD.setName(state.getName());
    return mapper.toDto(repository.save(stateD));
  }

  /**
   * Elimina (softdelete) un State de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return state eliminado.
   * @throws StateNotFoundException
   */
  @Override
  public State delete(Long id) throws StateNotFoundException {
    StateD stateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new StateNotFoundException("State not found."));
    return mapper.toDto(repository.softDelete(stateD));
  }
}