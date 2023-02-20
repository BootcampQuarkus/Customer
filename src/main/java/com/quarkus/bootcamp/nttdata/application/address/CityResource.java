package com.quarkus.bootcamp.nttdata.application.address;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.CityNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.StateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.address.City;
import com.quarkus.bootcamp.nttdata.domain.services.address.CityService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityResource {
  @Inject
  CityService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    try {
      return Response.ok(service.getById(id)).build();
    } catch (CityNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @POST
  @Transactional
  public Response create(City city) {
    try {
      return Response.ok(service.create(city)).status(201).build();
    } catch (StateNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, City city) {
    try {
      return Response.ok(service.update(id, city)).status(201).build();
    } catch (CityNotFoundException | StateNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    try {
      return Response.ok(service.delete(id)).build();
    } catch (CityNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }
}
