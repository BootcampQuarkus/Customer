package com.quarkus.bootcamp.nttdata.domain.interfaces;

import java.util.List;

/**
 * Esta interfaz indica todos los metodos que deben ser
 * implementados en cada uno de los servicios de esta
 * aplicación.
 *
 * @param <T> Clase Input
 * @param <U> Clase Output
 * @author pdiaz
 */
public interface IService<T, U> {
  /**
   * Retorna todos los elementos guardados en la bd
   * que no estén borrados (softDelete).
   *
   * @return Lista de elementos.
   */
  public List<U> getAll();

  /**
   * Se busca por el id en la bd y de existir
   * retorna el elemento, de lo contrario retorna
   * null
   *
   * @param id Id del elemento en la BD.
   * @return De existir el elemento lo retorna, de lo contrario null .
   */
  public U getById(Long id);

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param t El elemento a crear.
   * @return El elemento creado.
   */
  public U create(T t);

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id Identificador del elemento ha editar.
   * @param t  Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
  public U update(Long id, T t);

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  public U delete(Long id);
}