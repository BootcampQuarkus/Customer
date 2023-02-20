package com.quarkus.bootcamp.nttdata.application.customer;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.address.AddressNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.customer.BodyCorporateNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.customer.BodyCorporate;
import com.quarkus.bootcamp.nttdata.domain.services.customer.BodyCorporateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bodycorporate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BodyCorporateResource {
  @Inject
  BodyCorporateService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    try {
      return Response.ok(service.getById(id)).build();
    } catch (BodyCorporateNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @POST
  @Transactional
  public Response create(BodyCorporate bodyCorporate) {
    try {
      return Response.ok(service.create(bodyCorporate)).status(201).build();
    } catch (AddressNotFoundException | DocumentNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, BodyCorporate bodyCorporate) {
    try {
      return Response.ok(service.update(id, bodyCorporate)).status(201).build();
    } catch (BodyCorporateNotFoundException | DocumentNotFoundException | AddressNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    try {
      return Response.ok(service.delete(id)).build();
    } catch (BodyCorporateNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }
}
