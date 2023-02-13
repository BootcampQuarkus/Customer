package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateMapper implements IMapper<State, StateD> {
  /**
   * Transforma el objeto de CardType a CardTypeD.
   *
   * @param state Objeto de la clase CardType que se desea transformar.
   * @return Objeto de la clase CardTypeD.
   * @throws NullPointerException
   */
  @Override
  public StateD toDto(State state) throws NullPointerException {
    StateD stateD = new StateD();
    stateD.setName(state.getName());
    return stateD;
  }

  /**
   * Transforma el objeto de CardTypeD a CardType.
   *
   * @param stateD Objeto de la clase CardTypeD que se desea transformar.
   * @return Objeto de la clase CardType.
   * @throws NullPointerException
   */
  @Override
  public State toEntity(StateD stateD) throws NullPointerException {
    State state = new State();
    state.setId(stateD.id);
    state.setName(stateD.getName());
    return state;
  }
}
