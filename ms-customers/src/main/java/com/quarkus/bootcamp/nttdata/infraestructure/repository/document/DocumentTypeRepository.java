package com.quarkus.bootcamp.nttdata.infraestructure.repository.document;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentTypeD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DocumentTypeRepository implements IRepository<DocumentTypeD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<DocumentTypeD> getAll() {
    return DocumentTypeD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   */
  @Override
  public DocumentTypeD getById(Long id) {
    Optional<DocumentTypeD> documentTypeD = DocumentTypeD.findByIdOptional(id);
    if (documentTypeD.isEmpty()) {
      throw new NotFoundException("City not found");
    }
    return documentTypeD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param documentTypeD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public DocumentTypeD save(DocumentTypeD documentTypeD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (documentTypeD.getCreatedAt() == null) {
      documentTypeD.setCreatedAt(date);
    } else {
      documentTypeD.setUpdatedAt(date);
    }
    documentTypeD.persist();
    return documentTypeD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param documentTypeD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public DocumentTypeD softDelete(DocumentTypeD documentTypeD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    documentTypeD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(documentTypeD);
  }
}
