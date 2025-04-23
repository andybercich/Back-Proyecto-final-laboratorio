package org.example.Controllers;

import org.example.Entities.Descuento;
import org.example.Repositories.DescuentoRepository;
import org.example.Services.DescuentoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/descuento")
public class DescuentoController extends BaseController<Descuento,Long, DescuentoRepository, DescuentoService> {
    public DescuentoController(DescuentoService service) {
        super(service);
    }
}
