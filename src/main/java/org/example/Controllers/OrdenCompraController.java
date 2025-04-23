package org.example.Controllers;

import org.example.Entities.OrdenCompra;
import org.example.Repositories.OrdenCompraRepository;
import org.example.Services.OrdenCompraService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/ordenCompra")
public class OrdenCompraController extends BaseController<OrdenCompra,Long, OrdenCompraRepository, OrdenCompraService>{
    public OrdenCompraController(OrdenCompraService service) {
        super(service);
    }
}
