package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CityMapperTest {
  @Inject
  CityMapper mapper;

  @Test
  void toEntityNull() {
    City city = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(city));
  }

  @Test
  void toDtoNull() {
    CityD cityD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(cityD));
  }

  @Test
  void toEntityReturnEntity() {
    City city = new City();
    Assertions.assertInstanceOf(CityD.class, mapper.toEntity(city));
  }

  @Test
  void toDtoReturnDto() {
    StateD stateD = new StateD();
    CityD cityD = new CityD();
    cityD.setStateD(stateD);
    Assertions.assertInstanceOf(City.class, mapper.toDto(cityD));
  }

  @Test
  void toEntityVoid() {
    City city = new City();
    CityD expected = new CityD();
    CityD actual = mapper.toEntity(city);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoVoid() {
    CityD cityD = new CityD();
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(cityD));
  }

  @Test
  void toEntityValid() {
    City city = new City();
    city.setName("Ciudad");
    CityD expected = new CityD();
    expected.setName("Ciudad");
    CityD actual = mapper.toEntity(city);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoValid() {
    StateD stateD = new StateD();
    stateD.id = 1L;

    CityD cityD = new CityD();
    cityD.id = 2L;
    cityD.setName("Ciudad");
    cityD.setStateD(stateD);

    City expected = new City();
    expected.setId(2L);
    expected.setName("Ciudad");
    expected.setStateId(1L);

    City actual = mapper.toDto(cityD);
    Assertions.assertEquals(expected, actual);
  }
}