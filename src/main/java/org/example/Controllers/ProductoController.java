package org.example.Controllers;

import org.example.Entities.Producto;
import org.example.Repositories.ProductoRepository;
import org.example.Services.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/producto")
public class ProductoController extends BaseController<Producto,Long, ProductoRepository, ProductoService>{
    public ProductoController(ProductoService service) {
        super(service);
    }
}
