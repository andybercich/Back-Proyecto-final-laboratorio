package org.example.Controllers;

import org.example.Entities.Precio;
import org.example.Repositories.PrecioRepository;
import org.example.Services.PrecioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sneaks/precio")
public class PrecioController extends BaseController<Precio,Long, PrecioRepository, PrecioService>{
    public PrecioController(PrecioService service) {
        super(service);
    }

    //La fecha va a si pablix 2025-04-28
    @GetMapping("/fechaDescuento/{fecha}")
    public ResponseEntity<List<Precio>> getDetalleDescuentoByFecha(@PathVariable String fecha){
        try {
            LocalDate date = LocalDate.parse(fecha);
            System.out.println(fecha);
            return ResponseEntity.ok(service.getDetalleDescuentoByFecha(date));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
