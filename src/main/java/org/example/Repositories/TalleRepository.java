package org.example.Repositories;

import org.example.Entities.OrdenCompraDetalle;
import org.example.Entities.Talle;
import org.springframework.stereotype.Repository;

@Repository
public interface TalleRepository extends BaseRepository<Talle,Long> {
}
