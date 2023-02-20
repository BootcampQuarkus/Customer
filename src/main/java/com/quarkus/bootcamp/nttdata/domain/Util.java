package com.quarkus.bootcamp.nttdata.domain;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.domain.entity.document.DocumentType;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.AddressMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.CityMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.address.StateMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.BodyCorporateMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.NaturalPersonMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentMapper;
import com.quarkus.bootcamp.nttdata.domain.mapper.document.DocumentTypeMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.AddressRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.CityRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.address.StateRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.document.DocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Util {
  @Inject
  StateRepository sRepository;
  @Inject
  CityRepository cRepository;
  @Inject
  DocumentRepository dRepository;
  @Inject
  AddressRepository aRepository;
  @Inject
  CityMapper cMapper;
  @Inject
  StateMapper sMapper;
  @Inject
  AddressMapper aMapper;
  @Inject
  DocumentMapper dMapper;
  @Inject
  DocumentTypeMapper dtMapper;
  @Inject
  NaturalPersonMapper npMapper;
  @Inject
  BodyCorporateMapper bcMapper;

  /**
   * Retorna un estado/departamento con todos sus ciudades como entidades de lógica de negocio.
   *
   * @param stateD Objeto del tipo StateD. Estado/Departamento como entidad de BD.
   * @return Objeto State con los valores del parámetro.
   */
  public State stateWithCities(StateD stateD) {
    State state = sMapper.toDto(stateD);
    state.setCities(stateD.getCitiesD().stream()
          .filter(q -> (q.getDeletedAt() == null))
          .map(cMapper::toDto)
          .toList());
    return state;
  }

  public State stateWithCitiesAndAddress(StateD stateD) {
    State state = this.stateWithCities(stateD);
    state.setAddresses(stateD.getAddressDs().stream()
          .filter(q -> (q.getDeletedAt() == null))
          .map(aMapper::toDto).toList());
    return state;
  }

  /**
   * Se transforma un CityD a City con su State.
   *
   * @param cityD Objeto del tipo CityD
   * @return Objeto del tipo City con los datos del CityD.
   */
  public City cityWithState(CityD cityD) {
    City city = cMapper.toDto(cityD);
    city.setStates(sMapper.toDto(cityD.getStateD()));
    city.setStateId(city.getStates().getId());
    return city;
  }
  public City cityWithStateAndAddress(CityD cityD) {
    City city = this.cityWithState(cityD);
    city.setAddresses(cityD.getAddressDs().stream()
          .filter(q -> q.getDeletedAt() == null)
          .map(aMapper::toDto)
          .toList());
    return city;
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
    Address address = aMapper.toDto(addressD);
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

  public DocumentType documentTypeWithDocuments(DocumentTypeD documentTyped) {
    DocumentType documentType = dtMapper.toDto(documentTyped);
    documentType.setDocuments(documentTyped.getDocumentDs()
          .stream()
          .filter(q -> q.getDeletedAt() == null)
          .map(dMapper::toDto)
          .toList());
    return documentType;
  }

  public Document documentWithDocumentTypeAndId(Document document, DocumentD documentD) {
    document.setDocumentType(dtMapper.toDto(documentD.getDocumentTypeD()));
    document.setDocumentTypeId(document.getDocumentType().getId());
    return document;
  }

  public Document documentWithDocumentType(DocumentD documentD) {
    Document document = dMapper.toDto(documentD);
    document.setDocumentType(dtMapper.toDto(documentD.getDocumentTypeD()));
    return document;
  }

  public NaturalPerson naturalPersonWithDocumentAndAddress(NaturalPersonD naturalPersond) {
    NaturalPerson naturalPerson = this.naturalPersonWithAddressAndDocument(npMapper.toDto(naturalPersond), naturalPersond);
    naturalPerson.getAddress().setState(sMapper.toDto(naturalPersond.getAddressD().getStateD()));
    naturalPerson.getAddress().setCity(cMapper.toDto(naturalPersond.getAddressD().getCityD()));
    naturalPerson.setAddressId(naturalPerson.getAddress().getId());
    naturalPerson.getDocument().setDocumentType(dtMapper.toDto(naturalPersond.getDocumentD().getDocumentTypeD()));
    naturalPerson.setDocumentId(naturalPerson.getDocument().getId());
    return naturalPerson;
  }

  public NaturalPersonD naturalPersonDDWithAddressDAndDocumentD(NaturalPersonD naturalPersonD, NaturalPerson naturalPerson) throws AddressNotFoundException, DocumentNotFoundException {
    naturalPersonD.setAddressD(aRepository.findByIdOptional(naturalPerson.getAddressId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new AddressNotFoundException("Address not found.")));
    naturalPersonD.setDocumentD(dRepository.findByIdOptional(naturalPerson.getDocumentId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentNotFoundException("Document not found.")));
    return naturalPersonD;
  }

  public NaturalPerson naturalPersonWithAddressAndDocument(NaturalPerson naturalPerson, NaturalPersonD naturalPersonD) {
    naturalPerson.setAddress(aMapper.toDto(naturalPersonD.getAddressD()));
    naturalPerson.setDocument(dMapper.toDto(naturalPersonD.getDocumentD()));
    return naturalPerson;
  }

  public BodyCorporate bodyCorporateWithDocumentAddressAndIds(BodyCorporateD bodyCorporateD) {
    BodyCorporate bodyCorporate = bodyCorporateWithAddressAndDocument(bcMapper.toDto(bodyCorporateD), bodyCorporateD);
    bodyCorporate.getAddress().setState(sMapper.toDto(bodyCorporateD.getAddressD().getStateD()));
    bodyCorporate.getAddress().setCity(cMapper.toDto(bodyCorporateD.getAddressD().getCityD()));
    bodyCorporate.setAddressId(bodyCorporate.getAddress().getId());
    bodyCorporate.getDocument().setDocumentType(dtMapper.toDto(bodyCorporateD.getDocumentD().getDocumentTypeD()));
    bodyCorporate.setDocumentId(bodyCorporate.getDocument().getId());
    return bodyCorporate;
  }

  public BodyCorporateD bodyCorporateDWithAddressDAndDocumentD(BodyCorporateD bodyCorporateD, BodyCorporate bodyCorporate) throws AddressNotFoundException, DocumentNotFoundException {
    bodyCorporateD.setAddressD(aRepository.findByIdOptional(bodyCorporate.getAddressId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new AddressNotFoundException("Address not found.")));
    bodyCorporateD.setDocumentD(dRepository.findByIdOptional(bodyCorporate.getDocumentId())
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new DocumentNotFoundException("Document not found.")));
    return bodyCorporateD;
  }

  public BodyCorporate bodyCorporateWithAddressAndDocument(BodyCorporate bodyCorporate, BodyCorporateD bodyCorporateD) {
    bodyCorporate.setAddress(aMapper.toDto(bodyCorporateD.getAddressD()));
    bodyCorporate.setDocument(dMapper.toDto(bodyCorporateD.getDocumentD()));
    return bodyCorporate;
  }
}
