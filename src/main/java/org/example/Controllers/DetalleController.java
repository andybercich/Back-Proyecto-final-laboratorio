package org.example.Controllers;

import org.example.Entities.Detalle;
import org.example.Repositories.DetalleRepository;
import org.example.Services.DetalleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/detalle")
public class DetalleController extends BaseController<Detalle,Long, DetalleRepository, DetalleService>{
    public DetalleController(DetalleService service) {
        super(service);
    }
}
