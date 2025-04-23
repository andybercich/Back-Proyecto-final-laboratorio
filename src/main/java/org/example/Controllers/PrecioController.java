package org.example.Controllers;

import org.example.Entities.Precio;
import org.example.Repositories.PrecioRepository;
import org.example.Services.PrecioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/precio")
public class PrecioController extends BaseController<Precio,Long, PrecioRepository, PrecioService>{
    public PrecioController(PrecioService service) {
        super(service);
    }
}
