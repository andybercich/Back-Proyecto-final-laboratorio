package org.example.Controllers;

import org.example.Entities.OrdenCompraDetalle;
import org.example.Repositories.OrdenCompraDetalleRepository;
import org.example.Services.OrdenCompraDetalleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/ordenCompraDetalle")
public class OrdenCompraDetalleController extends BaseController<OrdenCompraDetalle,Long, OrdenCompraDetalleRepository,
        OrdenCompraDetalleService> {
    public OrdenCompraDetalleController(OrdenCompraDetalleService service) {
        super(service);
    }
}
