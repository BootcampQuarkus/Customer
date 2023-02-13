package com.quarkus.bootcamp.nttdata.application.customer;

import com.quarkus.bootcamp.nttdata.domain.entity.customer.NaturalPerson;
import com.quarkus.bootcamp.nttdata.domain.services.customer.NaturalPersonService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/naturalperson")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NaturalPersonResource {
  @Inject
  NaturalPersonService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    return Response.ok(service.getById(id)).build();
  }

  @POST
  @Transactional
  public Response create(NaturalPerson naturalPerson) {
    return Response.ok(service.create(naturalPerson)).status(201).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, NaturalPerson naturalPerson) {
    return Response.ok(service.update(id, naturalPerson)).status(201).build();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    return Response.ok(service.delete(id)).build();
  }
}
