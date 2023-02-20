package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Clase para transformaciones entre State y StateD
 *
 * @author pdiaz
 */
@ApplicationScoped
public class StateMapper implements IMapper<State, StateD> {
  /**
   * Transforma el objeto de State a StateD.
   *
   * @param state Objeto de la clase State que se desea transformar.
   * @return Objeto de la clase StateD.
   * @throws NullPointerException
   */
  @Override
  public StateD toEntity(State state) throws NullPointerException {
    StateD stateD = new StateD();
    stateD.setName(state.getName());
    return stateD;
  }

  /**
   * Transforma el objeto de StateD a State.
   *
   * @param stateD Objeto de la clase StateD que se desea transformar.
   * @return Objeto de la clase State.
   * @throws NullPointerException
   */
  @Override
  public State toDto(StateD stateD) throws NullPointerException {
    State state = new State();
    state.setId(stateD.id);
    state.setName(stateD.getName());
    return state;
  }
}
