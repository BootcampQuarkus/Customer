package com.quarkus.bootcamp.nttdata.infraestructure.repository.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los estados/departamentos de los clientes.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class StateRepository implements IRepository<StateD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<StateD> getAll() {
    return StateD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   * @throws StateNotFoundException
   */
  @Override
  public StateD getById(Long id) throws StateNotFoundException {
    Optional<StateD> cardD = StateD.findByIdOptional(id);
    if (cardD.isEmpty()) {
      throw new StateNotFoundException("State not found");
    }
    return cardD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param stateD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public StateD save(StateD stateD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (stateD.getCreatedAt() == null) {
      stateD.setCreatedAt(date);
    } else {
      stateD.setUpdatedAt(date);
    }
    stateD.persist();
    return stateD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param stateD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public StateD softDelete(StateD stateD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    stateD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(stateD);
  }
}
