package org.example.Controllers;

import org.example.Entities.DTO.OrdenCompraDTO;
import org.example.Entities.DTO.OrdenCompraDetalleDTO;
import org.example.Entities.OrdenCompra;
import org.example.Entities.OrdenCompraDetalle;
import org.example.Repositories.OrdenCompraDetalleRepository;
import org.example.Services.OrdenCompraDetalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sneaks/ordenCompraDetalle")
public class OrdenCompraDetalleController extends BaseController<OrdenCompraDetalle,Long, OrdenCompraDetalleRepository,
        OrdenCompraDetalleService> {
    public OrdenCompraDetalleController(OrdenCompraDetalleService service) {
        super(service);
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrdenCompraDetalleDTO>> getAllOrdenCompraDetalle() {
        try{
            List<OrdenCompraDetalle> ordenesComprasDetalle = service.findAll();
            return ResponseEntity.ok(OrdenCompraDetalleDTO.fromEntities(ordenesComprasDetalle));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
