package org.example.Repositories;

import org.example.Entities.Precio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrecioRepository extends BaseRepository<Precio, Long>{

    @Query("""
    SELECT DISTINCT p FROM Precio p
    JOIN p.detalle d
    JOIN p.descuento des
    WHERE :fechaActual BETWEEN des.fechaInicio AND des.fechaFin
    """)
    List<Precio> findPreciosConDescuentoActivo(@Param("fechaActual") LocalDate fechaActual);
}
