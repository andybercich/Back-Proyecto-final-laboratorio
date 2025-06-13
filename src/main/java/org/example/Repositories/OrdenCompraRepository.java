package org.example.Repositories;

import org.example.Entities.OrdenCompra;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends BaseRepository<OrdenCompra, Long>{

    List<OrdenCompra> findByUsuarioId(Long usuarioId);
}
