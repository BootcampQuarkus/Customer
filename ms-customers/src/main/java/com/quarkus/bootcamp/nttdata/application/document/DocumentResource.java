package com.quarkus.bootcamp.nttdata.application.document;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentTypeNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.document.Document;
import com.quarkus.bootcamp.nttdata.domain.services.document.DocumentService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentResource {
  @Inject
  DocumentService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    try {
      return Response.ok(service.getById(id)).build();
    } catch (DocumentNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @POST
  @Transactional
  public Response create(Document document) {
    try {
      return Response.ok(service.create(document)).status(201).build();
    } catch (DocumentTypeNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, Document document) {
    try {
      return Response.ok(service.update(id, document)).status(201).build();
    } catch (DocumentNotFoundException | DocumentTypeNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    try {
      return Response.ok(service.delete(id)).build();
    } catch (DocumentNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }
}
