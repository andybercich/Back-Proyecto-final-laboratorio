package org.example.Repositories;
import org.example.Entities.Detalle;
import org.example.Entities.Precio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleRepository extends BaseRepository<Detalle,Long> {

    List<Detalle> findByProductoId(Long productoId);

}
