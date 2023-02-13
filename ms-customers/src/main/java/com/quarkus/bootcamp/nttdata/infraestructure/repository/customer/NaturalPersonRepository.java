package com.quarkus.bootcamp.nttdata.infraestructure.repository.customer;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NaturalPersonRepository implements IRepository<NaturalPersonD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<NaturalPersonD> getAll() {
    return NaturalPersonD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   */
  @Override
  public NaturalPersonD getById(Long id) {
    Optional<NaturalPersonD> naturalPersonD = NaturalPersonD.findByIdOptional(id);
    if (naturalPersonD.isEmpty()) {
      throw new NotFoundException("City not found");
    }
    return naturalPersonD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param naturalPersonD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public NaturalPersonD save(NaturalPersonD naturalPersonD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (naturalPersonD.getCreatedAt() == null) {
      naturalPersonD.setCreatedAt(date);
    } else {
      naturalPersonD.setUpdatedAt(date);
    }
    naturalPersonD.persist();
    return naturalPersonD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param naturalPersonD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public NaturalPersonD softDelete(NaturalPersonD naturalPersonD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    naturalPersonD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(naturalPersonD);
  }
}
