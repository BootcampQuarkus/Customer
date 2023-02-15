package com.quarkus.bootcamp.nttdata.domain.services.customer;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.customer.NaturalPersonNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.NaturalPersonMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.customer.NaturalPersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Clase de servicio con las principales funciones para los resources.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class NaturalPersonService implements IService<NaturalPerson, NaturalPerson> {
  @Inject
  NaturalPersonRepository repository;
  @Inject
  NaturalPersonMapper mapper;
  @Inject
  Util util;

  /**
   * Retorna todas las personas naturales no eliminadas.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de personas naturales no eliminadas.
   */
  @Override
  public List<NaturalPerson> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::naturalPersonWithDocumentAndAddress)
          .toList();
  }

  /**
   * Retorna la persona natural si no está eliminada.
   * Se valida que el campo deleteAd sea nulo
   *
   * @param id Id del elemento en la BD.
   * @return persona natural no eliminada
   * @throws NaturalPersonNotFoundException
   */
  @Override
  public NaturalPerson getById(Long id) throws NaturalPersonNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::naturalPersonWithDocumentAndAddress)
          .orElseThrow(() -> new NaturalPersonNotFoundException("Natural Person not found."));
  }

  /**
   * Guarda una persona natural y retorna la bodyCorporate guardada.
   *
   * @param naturalPerson El elemento a crear.
   * @return persona natural creada.
   * @throws AddressNotFoundException
   * @throws DocumentNotFoundException
   */
  @Override
  public NaturalPerson create(NaturalPerson naturalPerson) throws AddressNotFoundException, DocumentNotFoundException {
    NaturalPersonD naturalPersonD = util.naturalPersonDDWithAddressDAndDocumentD(mapper.toEntity(naturalPerson), naturalPerson);
    return util.naturalPersonWithAddressAndDocument(mapper.toDto(repository.save(naturalPersonD)), naturalPersonD);
  }

  /**
   * Actualiza los datos de una persona natural previamente guardada.
   *
   * @param id            Identificador del elemento a editar.
   * @param naturalPerson Elemento con los datos para guardar.
   * @return persona natural actualizada.
   * @throws NaturalPersonNotFoundException
   * @throws AddressNotFoundException
   * @throws DocumentNotFoundException
   */
  @Override
  public NaturalPerson update(Long id, NaturalPerson naturalPerson) throws NaturalPersonNotFoundException, AddressNotFoundException, DocumentNotFoundException {
    NaturalPersonD naturalPersonD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NaturalPersonNotFoundException("Natural person not found."));
    naturalPersonD = util.naturalPersonDDWithAddressDAndDocumentD(naturalPersonD, naturalPerson);
    naturalPersonD.setName(naturalPerson.getName());
    naturalPersonD.setLastName(naturalPerson.getLastName());
    return util.naturalPersonWithAddressAndDocument(mapper.toDto(repository.save(naturalPersonD)), naturalPersonD);
  }

  /**
   * Elimina (softdelete) una persona naturales de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return persona natural eliminada.
   * @throws NaturalPersonNotFoundException
   */
  @Override
  public NaturalPerson delete(Long id) throws NaturalPersonNotFoundException {
    NaturalPersonD naturalPersonD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NaturalPersonNotFoundException("Natural person not found"));
    return util.naturalPersonWithAddressAndDocument(mapper.toDto(repository.softDelete(naturalPersonD)), naturalPersonD);
  }
}