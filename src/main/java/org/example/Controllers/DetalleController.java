package org.example.Controllers;

import org.example.Entities.DTO.DetalleDTO;
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

    //Body con el arreglo de las imagenes va este
    @PutMapping("/update")
    public ResponseEntity<?> updateDetalle (@RequestBody Detalle detalle){
        try {

            Detalle detalle1= service.updateDetalle(detalle);
            return ResponseEntity.ok(DetalleDTO.fromEntity(detalle1));

        }catch (Exception e){

            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }

    @PutMapping("/post")
    public ResponseEntity<?> postDetalle (@RequestBody Detalle detalle){
        try {

            Detalle detalle1= service.save(detalle);
            return ResponseEntity.ok(DetalleDTO.fromEntity(detalle1));

        }catch (Exception e){

            return  ResponseEntity.internalServerError().body("Error: "+e.getMessage());
        }
    }


}
