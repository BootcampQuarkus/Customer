package com.quarkus.bootcamp.nttdata.domain.services.address;

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
import jakarta.ws.rs.NotFoundException;

import java.util.List;

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

  @Override
  public List<Address> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            Address address = mapper.toEntity(p);
            address.setState(sMapper.toEntity(p.getStateD()));
            address.setStateId(address.getState().getId());
            address.setCity(cMapper.toEntity(p.getCityD()));
            address.setCityId(address.getCity().getId());
            return address;
          })
          .toList();
  }

  @Override
  public Address getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            Address address = mapper.toEntity(p);
            address.setState(sMapper.toEntity(p.getStateD()));
            address.setStateId(address.getState().getId());
            address.setCity(cMapper.toEntity(p.getCityD()));
            address.setCityId(address.getCity().getId());
            return address;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public Address create(Address address) {
    AddressD addressD = mapper.toDto(address);
    addressD.setCityD(cRepository.findByIdOptional(address.getCityId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException()));
    addressD.setStateD(sRepository.findByIdOptional(address.getStateId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException()));
    address = mapper.toEntity(repository.save(addressD));
    address.setState(sMapper.toEntity(addressD.getStateD()));
    address.setCity(cMapper.toEntity(addressD.getCityD()));
    return address;
  }

  @Override
  public Address update(Long id, Address address) {
    AddressD addressD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    addressD.setAddress(address.getAddress());
    addressD.setCityD(cRepository.getById(address.getCityId()));
    addressD.setStateD(sRepository.getById(address.getStateId()));
    address = mapper.toEntity(repository.save(addressD));
    address.setState(sMapper.toEntity(addressD.getStateD()));
    address.setCity(cMapper.toEntity(addressD.getCityD()));
    return address;
  }

  @Override
  public Address delete(Long id) {
    AddressD addressD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    Address address = mapper.toEntity(repository.softDelete(addressD));
    address.setState(sMapper.toEntity(addressD.getStateD()));
    address.setCity(cMapper.toEntity(addressD.getCityD()));
    return address;
  }
}
