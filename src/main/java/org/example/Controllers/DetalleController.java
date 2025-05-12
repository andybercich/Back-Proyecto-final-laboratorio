package org.example.Controllers;

import org.example.Entities.Detalle;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.example.Services.DetalleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sneaks/detalle")
public class DetalleController extends BaseController<Detalle,Long, DetalleRepository, DetalleService>{
    public DetalleController(DetalleService service) {
        super(service);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Long id){
        try {
            List<Detalle> detalles = service.findAllDetalleByIdProducto(id);
            return  ResponseEntity.ok(detalles);
        }catch (RuntimeException e){
            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }


}
