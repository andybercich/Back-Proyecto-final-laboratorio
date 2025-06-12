package org.example.Repositories;

import org.example.Entities.OrdenCompraDetalle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraDetalleRepository extends BaseRepository<OrdenCompraDetalle, Long> {

    public List<OrdenCompraDetalle> findByOrdenCompraId(Long id);
}
