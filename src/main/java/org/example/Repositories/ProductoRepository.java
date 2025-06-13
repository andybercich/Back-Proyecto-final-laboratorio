package org.example.Repositories;

import org.example.Entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto,Long>{

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :word1, '%')) AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :word2, '%')) AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :word3, '%'))")
    List<Producto> buscarPorNombreAvanzado(@Param("word1") String palabra1, @Param("word2") String palabra2, @Param("word3") String palabra3);


}
