package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class StateService implements IService<State, State> {
  @Inject
  StateRepository repository;
  @Inject
  StateMapper mapper;
  @Inject
  CityMapper cMapper;

  @Override
  public List<State> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            State state = mapper.toEntity(p);
            state.setCities(p.getCitiesD().stream()
                  .filter(q -> (q.getDeletedAt() == null))
                  .map(q -> cMapper.toEntity(q))
                  .toList());
            return state;
          })
          .toList();
  }

  @Override
  public State getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            State state = mapper.toEntity(p);
            state.setCities(p.getCitiesD().stream()
                  .filter(q -> (q.getDeletedAt() == null))
                  .map(q -> cMapper.toEntity(q))
                  .toList());
            return state;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public State create(State state) {
    return mapper.toEntity(repository.save(mapper.toDto(state)));
  }

  @Override
  public State update(Long id, State state) {
    StateD stateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    stateD.setName(state.getName());
    return mapper.toEntity(repository.save(stateD));
  }

  @Override
  public State delete(Long id) {
    StateD stateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(stateD));
  }
}