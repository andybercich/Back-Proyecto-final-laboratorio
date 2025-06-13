package org.example.Controllers;

import org.example.Entities.DTO.ProductoFindDTO;
import org.example.Entities.Producto;
import org.example.Repositories.ProductoRepository;
import org.example.Services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sneaks/producto")
public class ProductoController extends BaseController<Producto,Long, ProductoRepository, ProductoService>{
    public ProductoController(ProductoService service) {
        super(service);
    }

    @GetMapping("/buscarAgrupado/{param}")
    public ResponseEntity<?> buscarDetallesAgrupados(@PathVariable String param) {
        List<ProductoFindDTO> productos = service.findProductoConDetalles(param);
        return ResponseEntity.ok(productos);
    }
}
