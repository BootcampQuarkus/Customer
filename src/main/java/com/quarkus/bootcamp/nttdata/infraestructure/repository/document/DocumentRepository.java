package com.quarkus.bootcamp.nttdata.infraestructure.repository.document;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.document.DocumentNotFoundException;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.IRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los documentos.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class DocumentRepository implements IRepository<DocumentD> {
  /**
   * Se encarga de devolver todos los elementos no eliminados (softdelete)
   * de la BD.
   *
   * @return Lista de elementos no eliminados.
   */
  @Override
  public List<DocumentD> getAll() {
    return DocumentD.listAll();
  }

  /**
   * Se encarga de devolver el elemento solicitado por el identificador
   * siempre y cuando no este eliminado (softDelete).
   *
   * @param id Identificador del elemento a devolver.
   * @return Elemento encontrado.
   * @throws DocumentNotFoundException
   */
  @Override
  public DocumentD getById(Long id) throws DocumentNotFoundException {
    Optional<DocumentD> documentD = DocumentD.findByIdOptional(id);
    if (documentD.isEmpty()) {
      throw new DocumentNotFoundException("Document not found");
    }
    return documentD.get();
  }

  /**
   * Guarda el elemento en la base de datos (creacion y actualización).
   *
   * @param documentD Elemento a guardar.
   * @return Retorna el elemento guardado.
   */
  @Override
  public DocumentD save(DocumentD documentD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (documentD.getCreatedAt() == null) {
      documentD.setCreatedAt(date);
    } else {
      documentD.setUpdatedAt(date);
    }
    documentD.persist();
    return documentD;
  }

  /**
   * Realiza la eliminación logica del elemento
   *
   * @param documentD Elemento a borrar logicamente.
   * @return Elemento borrado logicamente.
   */
  @Override
  public DocumentD softDelete(DocumentD documentD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    documentD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(documentD);
  }
}
