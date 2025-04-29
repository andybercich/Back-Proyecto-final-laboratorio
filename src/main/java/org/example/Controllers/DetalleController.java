package org.example.Controllers;

import org.example.Entities.Detalle;
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

    //La fecha va a si pablix 2025-04-28
    @GetMapping("/fechaDescuento/{fecha}")
    public ResponseEntity<List<Detalle>> getDetalleDescuentoByFecha(@PathVariable String fecha){
        try {
            LocalDate date = LocalDate.parse(fecha);
            return ResponseEntity.ok(service.getDetalleDescuentoByFecha(date));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
