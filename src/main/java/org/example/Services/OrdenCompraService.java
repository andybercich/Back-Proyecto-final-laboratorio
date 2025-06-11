package org.example.Services;

import org.example.Entities.Detalle;
import org.example.Entities.OrdenCompra;
import org.example.Entities.OrdenCompraDetalle;
import org.example.Entities.Producto;
import org.example.Repositories.DetalleRepository;
import org.example.Repositories.OrdenCompraDetalleRepository;
import org.example.Repositories.OrdenCompraRepository;
import org.example.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrdenCompraService extends BaseService<OrdenCompra,Long, OrdenCompraRepository >{

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;
}
