package com.quarkus.bootcamp.nttdata.domain.mapper.address;

import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.CityD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.StateD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AddressMapperTest {
  @Inject
  AddressMapper mapper;

  @Test
  void toEntityNull() {
    Address address = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(address));
  }

  @Test
  void toDtoNull() {
    AddressD addressD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(addressD));
  }

  @Test
  void toEntityReturnEntity() {
    Address address = new Address();
    Assertions.assertInstanceOf(AddressD.class, mapper.toEntity(address));
  }

  @Test
  void toDtoReturnDto() {
    StateD stateD = new StateD();
    CityD cityD = new CityD();
    AddressD addressD = new AddressD();
    addressD.setCityD(cityD);
    addressD.setStateD(stateD);
    Assertions.assertInstanceOf(Address.class, mapper.toDto(addressD));
  }

  @Test
  void toEntityVoid() {
    Address address = new Address();
    AddressD expected = new AddressD();
    AddressD actual = mapper.toEntity(address);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoVoid() {
    AddressD addressD = new AddressD();
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(addressD));
  }

  @Test
  void toEntityValid() {
    Address address = new Address();
    address.setAddress("Dirección");
    AddressD expected = new AddressD();
    expected.setAddress("Dirección");
    AddressD actual = mapper.toEntity(address);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void toDtoValid() {
    StateD stateD = new StateD();
    stateD.id = 1L;
    CityD cityD = new CityD();
    cityD.id = 2L;
    AddressD addressD = new AddressD();
    addressD.id = 3L;
    addressD.setAddress("Dirección");
    addressD.setStateD(stateD);
    addressD.setCityD(cityD);

    Address expected = new Address();
    expected.setId(3L);
    expected.setAddress("Dirección");
    expected.setStateId(1L);
    expected.setCityId(2L);

    Address actual = mapper.toDto(addressD);
    Assertions.assertEquals(expected, actual);
  }
}