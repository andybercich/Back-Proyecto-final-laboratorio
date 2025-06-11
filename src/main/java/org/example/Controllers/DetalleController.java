package org.example.Controllers;

import org.example.Entities.DTO.DetalleDTO;
import org.example.Entities.Detalle;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.example.Services.DetalleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sneaks/detalle")
public class DetalleController extends BaseController<Detalle,Long, DetalleRepository, DetalleService>{
    public DetalleController(DetalleService service) {
        super(service);
    }

    public ResponseEntity<?> findAll(){
        try {
            return ResponseEntity.ok(DetalleDTO.fromEntitys(service.findAll()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/unicos/filtros")
    public ResponseEntity<?> obtenerConFiltros(
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false) Sexo sexo,
            @RequestParam(required = false) TipoProducto tipo,
            @RequestParam(required = false) Long idTalle,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String fechaDescuento,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max
    ) {
        try {
            return ResponseEntity.ok(
                    service.getDetallesUnicosConFiltros(estado, sexo, tipo, idTalle, categoria, fechaDescuento, min, max)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


    //Cambiamos los url por el bucle infinito Pablo
    @GetMapping("/producto/{id}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Long id){
        try {
            List<Detalle> detalles = service.findAllDetalleByIdProducto(id);
            return  ResponseEntity.ok(DetalleDTO.fromEntitys(detalles));
        }catch (Exception e){
            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/conDescuento/{fecha}")
    public ResponseEntity<?> obtenerDetallesConDescuento(@PathVariable String fecha) {
        try {
            List<Detalle> detalles = service.findDetallesConDescuentoActivo(fecha);
            return ResponseEntity.ok(DetalleDTO.fromEntitys(detalles));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }



    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {

            Detalle detalle1= service.findById(id);
            return ResponseEntity.ok(DetalleDTO.fromEntity(detalle1));

        }catch (Exception e){

            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        try {

            List<Detalle> detalle1= service.findAll();
            return ResponseEntity.ok(DetalleDTO.fromEntitys(detalle1));

        }catch (Exception e){

            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }



    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Detalle detalle){
        try{
            return ResponseEntity.ok(DetalleDTO.fromEntity(service.updateDetalle(detalle, id)));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create (@RequestBody Detalle detalle){
        try {

            Detalle detalle1= service.save(detalle);
            return ResponseEntity.ok(DetalleDTO.fromEntity(detalle1));

        }catch (Exception e){

            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }


}
