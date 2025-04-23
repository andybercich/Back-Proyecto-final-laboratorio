package org.example.Services;

import org.example.Entities.OrdenCompra;
import org.example.Entities.OrdenCompraDetalle;
import org.example.Repositories.OrdenCompraDetalleRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenCompraDetalleService extends BaseService<OrdenCompraDetalle, Long, OrdenCompraDetalleRepository>{
}
