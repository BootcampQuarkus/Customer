package com.quarkus.bootcamp.nttdata.infraestructure.repository.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
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
public class CityRepository implements IRepository<CityD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<CityD> getAll() {
    return CityD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   * @throws CityNotFoundException
   */
  @Override
  public CityD getById(Long id) throws CityNotFoundException {
    Optional<CityD> cityD = CityD.findByIdOptional(id);
    if (cityD.isEmpty()) {
      throw new CityNotFoundException("City not found");
    }
    return cityD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param cityD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public CityD save(CityD cityD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (cityD.getCreatedAt() == null) {
      cityD.setCreatedAt(date);
    } else {
      cityD.setUpdatedAt(date);
    }
    cityD.persist();
    return cityD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param cityD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public CityD softDelete(CityD cityD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    cityD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(cityD);
  }
}
