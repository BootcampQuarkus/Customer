package com.quarkus.bootcamp.nttdata.domain.services.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.AddressMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.NaturalPersonMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.AddressRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.customer.NaturalPersonRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class NaturalPersonService implements IService<NaturalPerson, NaturalPerson> {
  @Inject
  NaturalPersonRepository repository;
  @Inject
  DocumentRepository dRepository;
  @Inject
  AddressRepository aRepository;
  @Inject
  NaturalPersonMapper mapper;
  @Inject
  DocumentMapper dMapper;
  @Inject
  DocumentTypeMapper dtMapper;
  @Inject
  AddressMapper aMapper;
  @Inject
  StateMapper sMapper;
  @Inject
  CityMapper cMapper;

  @Override
  public List<NaturalPerson> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            NaturalPerson naturalPerson = mapper.toEntity(p);

            naturalPerson.setDocument(dMapper.toEntity(p.getDocumentD()));
            naturalPerson.getDocument().setDocumentType(dtMapper.toEntity(p.getDocumentD().getDocumentTypeD()));
            naturalPerson.setDocumentId(naturalPerson.getDocument().getId());

            naturalPerson.setAddress(aMapper.toEntity(p.getAddressD()));
            naturalPerson.getAddress().setState(sMapper.toEntity(p.getAddressD().getStateD()));
            naturalPerson.getAddress().setCity(cMapper.toEntity(p.getAddressD().getCityD()));
            naturalPerson.setAddressId(naturalPerson.getAddress().getId());

            return naturalPerson;
          })
          .toList();
  }

  @Override
  public NaturalPerson getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            NaturalPerson naturalPerson = mapper.toEntity(p);

            naturalPerson.setDocument(dMapper.toEntity(p.getDocumentD()));
            naturalPerson.getDocument().setDocumentType(dtMapper.toEntity(p.getDocumentD().getDocumentTypeD()));
            naturalPerson.setDocumentId(naturalPerson.getDocument().getId());

            naturalPerson.setAddress(aMapper.toEntity(p.getAddressD()));
            naturalPerson.getAddress().setState(sMapper.toEntity(p.getAddressD().getStateD()));
            naturalPerson.getAddress().setCity(cMapper.toEntity(p.getAddressD().getCityD()));
            naturalPerson.setAddressId(naturalPerson.getAddress().getId());

            return naturalPerson;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public NaturalPerson create(NaturalPerson naturalPerson) {
    NaturalPersonD naturalPersonD = mapper.toDto(naturalPerson);
    try {
      naturalPersonD.setAddressD(aRepository.findByIdOptional(naturalPerson.getAddressId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
      naturalPersonD.setDocumentD(dRepository.findByIdOptional(naturalPerson.getDocumentId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (Exception e) {
      throw new NotFoundException("Not found");
    }
    naturalPerson = mapper.toEntity(repository.save(naturalPersonD));
    naturalPerson.setAddress(aMapper.toEntity(naturalPersonD.getAddressD()));
    naturalPerson.setDocument(dMapper.toEntity(naturalPersonD.getDocumentD()));
    return naturalPerson;
  }

  @Override
  public NaturalPerson update(Long id, NaturalPerson naturalPerson) {
    NaturalPersonD naturalPersonD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    naturalPersonD.setName(naturalPerson.getName());
    naturalPersonD.setLastName(naturalPerson.getLastName());
    try {
      naturalPersonD.setAddressD(aRepository.findByIdOptional(naturalPerson.getAddressId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
      naturalPersonD.setDocumentD(dRepository.findByIdOptional(naturalPerson.getDocumentId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (Exception e) {
      throw new NotFoundException("Not found");
    }
    naturalPerson = mapper.toEntity(repository.save(naturalPersonD));
    naturalPerson.setAddress(aMapper.toEntity(naturalPersonD.getAddressD()));
    naturalPerson.setDocument(dMapper.toEntity(naturalPersonD.getDocumentD()));
    return naturalPerson;
  }

  @Override
  public NaturalPerson delete(Long id) {
    NaturalPersonD naturalPersonD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    NaturalPerson naturalPerson = mapper.toEntity(repository.softDelete(naturalPersonD));
    naturalPerson.setAddress(aMapper.toEntity(naturalPersonD.getAddressD()));
    naturalPerson.setDocument(dMapper.toEntity(naturalPersonD.getDocumentD()));
    return naturalPerson;
  }
}
