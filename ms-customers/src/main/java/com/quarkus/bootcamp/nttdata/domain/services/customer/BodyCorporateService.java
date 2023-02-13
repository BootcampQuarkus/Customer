package com.quarkus.bootcamp.nttdata.domain.services.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.AddressMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.BodyCorporateMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.AddressRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.customer.BodyCorporateRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class BodyCorporateService implements IService<BodyCorporate, BodyCorporate> {
  @Inject
  BodyCorporateRepository repository;
  @Inject
  DocumentRepository dRepository;
  @Inject
  AddressRepository aRepository;
  @Inject
  BodyCorporateMapper mapper;
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
  public List<BodyCorporate> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            BodyCorporate bodyCorporate = mapper.toEntity(p);

            bodyCorporate.setDocument(dMapper.toEntity(p.getDocumentD()));
            bodyCorporate.getDocument().setDocumentType(dtMapper.toEntity(p.getDocumentD().getDocumentTypeD()));
            bodyCorporate.setDocumentId(bodyCorporate.getDocument().getId());

            bodyCorporate.setAddress(aMapper.toEntity(p.getAddressD()));
            bodyCorporate.getAddress().setState(sMapper.toEntity(p.getAddressD().getStateD()));
            bodyCorporate.getAddress().setCity(cMapper.toEntity(p.getAddressD().getCityD()));
            bodyCorporate.setAddressId(bodyCorporate.getAddress().getId());

            return bodyCorporate;
          })
          .toList();
  }

  @Override
  public BodyCorporate getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> {
            BodyCorporate bodyCorporate = mapper.toEntity(p);

            bodyCorporate.setDocument(dMapper.toEntity(p.getDocumentD()));
            bodyCorporate.getDocument().setDocumentType(dtMapper.toEntity(p.getDocumentD().getDocumentTypeD()));
            bodyCorporate.setDocumentId(bodyCorporate.getDocument().getId());

            bodyCorporate.setAddress(aMapper.toEntity(p.getAddressD()));
            bodyCorporate.getAddress().setState(sMapper.toEntity(p.getAddressD().getStateD()));
            bodyCorporate.getAddress().setCity(cMapper.toEntity(p.getAddressD().getCityD()));
            bodyCorporate.setAddressId(bodyCorporate.getAddress().getId());

            return bodyCorporate;
          })
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public BodyCorporate create(BodyCorporate bodyCorporate) {
    BodyCorporateD bodyCorporateD = mapper.toDto(bodyCorporate);
    try {
      bodyCorporateD.setAddressD(aRepository.findByIdOptional(bodyCorporate.getAddressId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
      bodyCorporateD.setDocumentD(dRepository.findByIdOptional(bodyCorporate.getDocumentId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (Exception e) {
      throw new NotFoundException("Not found");
    }
    bodyCorporate = mapper.toEntity(repository.save(bodyCorporateD));
    bodyCorporate.setAddress(aMapper.toEntity(bodyCorporateD.getAddressD()));
    bodyCorporate.setDocument(dMapper.toEntity(bodyCorporateD.getDocumentD()));
    return bodyCorporate;
  }

  @Override
  public BodyCorporate update(Long id, BodyCorporate bodyCorporate) {
    BodyCorporateD bodyCorporateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    bodyCorporateD.setName(bodyCorporate.getName());
    try {
      bodyCorporateD.setAddressD(aRepository.findByIdOptional(bodyCorporate.getAddressId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
      bodyCorporateD.setDocumentD(dRepository.findByIdOptional(bodyCorporate.getDocumentId())
            .filter(p -> (p.getDeletedAt() == null))
            .orElseThrow(() -> new NotFoundException()));
    } catch (Exception e) {
      throw new NotFoundException("Not found");
    }
    bodyCorporate = mapper.toEntity(repository.save(bodyCorporateD));
    bodyCorporate.setAddress(aMapper.toEntity(bodyCorporateD.getAddressD()));
    bodyCorporate.setDocument(dMapper.toEntity(bodyCorporateD.getDocumentD()));
    return bodyCorporate;
  }

  @Override
  public BodyCorporate delete(Long id) {
    BodyCorporateD bodyCorporateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    BodyCorporate bodyCorporate = mapper.toEntity(repository.softDelete(bodyCorporateD));
    bodyCorporate.setAddress(aMapper.toEntity(bodyCorporateD.getAddressD()));
    bodyCorporate.setDocument(dMapper.toEntity(bodyCorporateD.getDocumentD()));
    return bodyCorporate;
  }
}
