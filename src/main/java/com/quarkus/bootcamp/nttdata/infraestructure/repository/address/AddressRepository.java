package com.quarkus.bootcamp.nttdata.infraestructure.repository.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para las direcciones de los clientes.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class AddressRepository implements IRepository<AddressD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<AddressD> getAll() {
    return AddressD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   * @throws AddressNotFoundException
   */
  @Override
  public AddressD getById(Long id) throws AddressNotFoundException {
    Optional<AddressD> addressD = AddressD.findByIdOptional(id);
    if (addressD.isEmpty()) {
      throw new AddressNotFoundException("Address not found");
    }
    return addressD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param addressD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public AddressD save(AddressD addressD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (addressD.getCreatedAt() == null) {
      addressD.setCreatedAt(date);
    } else {
      addressD.setUpdatedAt(date);
    }
    addressD.persist();
    return addressD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param addressD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public AddressD softDelete(AddressD addressD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    addressD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(addressD);
  }
}
