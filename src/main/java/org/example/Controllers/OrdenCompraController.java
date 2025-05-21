package org.example.Controllers;

import org.example.Entities.DTO.OrdenCompraDTO;
import org.example.Entities.OrdenCompra;
import org.example.Repositories.OrdenCompraRepository;
import org.example.Services.OrdenCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sneaks/ordenCompra")
public class OrdenCompraController extends BaseController<OrdenCompra,Long, OrdenCompraRepository, OrdenCompraService>{
    public OrdenCompraController(OrdenCompraService service) {
        super(service);
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrdenCompraDTO>> getAllOrdenCompra() {
        try{
            List<OrdenCompra> ordenesCompras = service.findAll();
            return ResponseEntity.ok(OrdenCompraDTO.fromEntities(ordenesCompras));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
