package com.quarkus.bootcamp.nttdata.infraestructure.repository.customer;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BodyCorporateRepository implements IRepository<BodyCorporateD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<BodyCorporateD> getAll() {
    return BodyCorporateD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   */
  @Override
  public BodyCorporateD getById(Long id) {
    Optional<BodyCorporateD> bodyCorporateD = BodyCorporateD.findByIdOptional(id);
    if (bodyCorporateD.isEmpty()) {
      throw new NotFoundException("City not found");
    }
    return bodyCorporateD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param bodyCorporateD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public BodyCorporateD save(BodyCorporateD bodyCorporateD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (bodyCorporateD.getCreatedAt() == null) {
      bodyCorporateD.setCreatedAt(date);
    } else {
      bodyCorporateD.setUpdatedAt(date);
    }
    bodyCorporateD.persist();
    return bodyCorporateD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param bodyCorporateD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public BodyCorporateD softDelete(BodyCorporateD bodyCorporateD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    bodyCorporateD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(bodyCorporateD);
  }
}
