package com.quarkus.bootcamp.nttdata.domain.services.customer;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.customer.BodyCorporateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.customer.BodyCorporateMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.customer.BodyCorporateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Clase de servicio con las principales funciones para los resources.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class BodyCorporateService implements IService<BodyCorporate, BodyCorporate> {
  @Inject
  BodyCorporateRepository repository;
  @Inject
  BodyCorporateMapper mapper;
  @Inject
  Util util;

  /**
   * Retorna todas las personas jurídicas no eliminadas.
   * Se valida que el campo deletedAt sea nulo.
   *
   * @return Lista de personas jurídicas no eliminadas.
   */
  @Override
  public List<BodyCorporate> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::bodyCorporateWithDocumentAddressAndIds)
          .toList();
  }

  /**
   * Retorna la persona jurídicas si no está eliminada.
   * Se valida que el campo deleteAd sea nulo.
   *
   * @param id Id del elemento en la BD.
   * @return persona jurídicas no eliminada.
   * @throws BodyCorporateNotFoundException
   */
  @Override
  public BodyCorporate getById(Long id) throws BodyCorporateNotFoundException {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(util::bodyCorporateWithDocumentAddressAndIds)
          .orElseThrow(() -> new BodyCorporateNotFoundException("Body corporate not found."));
  }

  /**
   * Guarda una persona jurídicas y retorna la bodyCorporate guardada.
   *
   * @param bodyCorporate El elemento a crear.
   * @return persona jurídicas creada.
   * @throws AddressNotFoundException
   * @throws DocumentNotFoundException
   */
  @Override
  public BodyCorporate create(BodyCorporate bodyCorporate) throws AddressNotFoundException, DocumentNotFoundException {
    BodyCorporateD bodyCorporateD = util.bodyCorporateDWithAddressDAndDocumentD(mapper.toEntity(bodyCorporate), bodyCorporate);
    return util.bodyCorporateWithAddressAndDocument(mapper.toDto(repository.save(bodyCorporateD)), bodyCorporateD);
  }

  /**
   * Actualiza los datos de una persona jurídicas previamente guardada.
   *
   * @param id            Identificador del elemento a editar.
   * @param bodyCorporate Elemento con los datos para guardar.
   * @return persona juridica actualizada.
   * @throws BodyCorporateNotFoundException
   * @throws AddressNotFoundException
   * @throws DocumentNotFoundException
   */
  @Override
  public BodyCorporate update(Long id, BodyCorporate bodyCorporate) throws BodyCorporateNotFoundException, AddressNotFoundException, DocumentNotFoundException {
    BodyCorporateD bodyCorporateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new BodyCorporateNotFoundException("Body corporate not found."));
    bodyCorporateD = util.bodyCorporateDWithAddressDAndDocumentD(bodyCorporateD, bodyCorporate);
    bodyCorporateD.setName(bodyCorporate.getName());
    return util.bodyCorporateWithAddressAndDocument(mapper.toDto(repository.save(bodyCorporateD)), bodyCorporateD);
  }

  /**
   * Elimina (softdelete) una persona jurídicas de la BD.
   *
   * @param id Identificador del elemento a eliminar.
   * @return persona jurídicas eliminada.
   * @throws BodyCorporateNotFoundException
   */
  @Override
  public BodyCorporate delete(Long id) throws BodyCorporateNotFoundException {
    BodyCorporateD bodyCorporateD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new BodyCorporateNotFoundException("Body corporate not found."));
    return util.bodyCorporateWithAddressAndDocument(mapper.toDto(repository.softDelete(bodyCorporateD)), bodyCorporateD);
  }
}
