package com.quarkus.bootcamp.nttdata.application.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.address.Address;
import com.quarkus.bootcamp.nttdata.domain.services.address.AddressService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/address")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {
  @Inject
  AddressService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    try {
      return Response.ok(service.getById(id)).build();
    } catch (AddressNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }

  }

  @POST
  @Transactional
  public Response create(Address address) {
    try {
      return Response.ok(service.create(address)).status(201).build();
    } catch (StateNotFoundException | CityNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, Address address) {
    try {
      return Response.ok(service.update(id, address)).status(201).build();
    } catch (StateNotFoundException | AddressNotFoundException | CityNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    try {
      return Response.ok(service.delete(id)).build();
    } catch (AddressNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }
}
