package com.quarkus.bootcamp.nttdata.domain.services.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.CityRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CityService implements IService<City, City> {
  @Inject
  CityRepository repository;
  @Inject
  StateRepository sRepository;
  @Inject
  CityMapper mapper;
  @Inject
  StateMapper sMapper;

  @Override
  public List<City> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            City city = mapper.toEntity(p);
            city.setStates(sMapper.toEntity(p.getStateD()));
            city.setStateId(city.getStates().getId());
            return city;
          })
          .toList();
  }

  @Override
  public City getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            City city = mapper.toEntity(p);
            city.setStates(sMapper.toEntity(p.getStateD()));
            city.setStateId(city.getStates().getId());
            return city;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public City create(City city) {
    CityD cityD = mapper.toDto(city);
    try {
      cityD.setStateD(sRepository.findByIdOptional(city.getStateId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (NotFoundException e) {
      throw new NotFoundException("State not found");
    }
    return mapper.toEntity(repository.save(cityD));
  }

  @Override
  public City update(Long id, City city) {
    CityD cityD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    try {
      cityD.setStateD(sRepository.getById(city.getStateId()));
    } catch (NotFoundException e) {
      throw new NotFoundException("State not found");
    }
    cityD.setName(city.getName());
    return mapper.toEntity(repository.save(cityD));
  }

  @Override
  public City delete(Long id) {
    CityD cityD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(cityD));
  }
}

