package com.quarkus.bootcamp.nttdata.domain.interfaces;

/**
 * Esta interfaz indica los metodos necesarios que deben ser
 * implementados en cada uno de los mappers de la aplicación.
 *
 * @param <T>
 * @param <U>
 * @author pdiaz
 */
public interface IMapper<T, U> {
  /**
   * Transforma el objeto de T a U.
   *
   * @param t Objeto de la clase T que se desea transformar.
   * @return Objeto de la clase U.
   */
  U toDto(T t) throws NullPointerException;

  /**
   * Transforma el objeto de U a T.
   *
   * @param u Objeto de la clase U que se desea transformar.
   * @return Objeto de la clase T.
   */

  T toEntity(U u) throws NullPointerException;
}
