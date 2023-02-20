package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.State;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StateMapperTest {
  @Inject
  StateMapper mapper;

  @Test
  void toEntityNull() {
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(null));
  }

  @Test
  void toDtoNull() {
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(null));
  }

  @Test
  void toEntityReturnEntity() {
    State state = new State();
    Assertions.assertInstanceOf(StateD.class, mapper.toEntity(state));
  }

  @Test
  void toDtoReturnDto() {
    StateD stateD = new StateD();
    Assertions.assertInstanceOf(State.class, mapper.toDto(stateD));
  }

  @Test
  void toEntityVoid() {
    State state = new State();
    StateD expected = new StateD();
    StateD actual = mapper.toEntity(state);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoVoid() {
    StateD state = new StateD();
    State expected = new State();
    State actual = mapper.toDto(state);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toEntityValid() {
    State state = new State();
    state.setName("Departamento");
    StateD expected = new StateD();
    expected.setName("Departamento");
    StateD actual = mapper.toEntity(state);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoValid() {
    StateD stateD = new StateD();
    stateD.setName("Departamento");
    State expected = new State();
    expected.setName("Departamento");
    State actual = mapper.toDto(stateD);
    Assertions.assertEquals(expected, actual);
  }
}