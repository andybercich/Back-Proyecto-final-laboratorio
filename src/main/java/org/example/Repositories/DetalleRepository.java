package org.example.Repositories;
import org.example.Entities.Detalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleRepository extends BaseRepository<Detalle,Long> {

    @Query("""
    SELECT DISTINCT d FROM Detalle d
    JOIN Precio p ON p.detalle = d
    JOIN p.descuento des
    WHERE :fechaActual BETWEEN des.fechaInicio AND des.fechaFin
    """)
    List<Detalle> findDetallesConDescuentoActivo(@Param("fechaActual") LocalDate fechaActual);

}
