package org.example.Controllers;

import org.example.Entities.DTO.OrdenCompraDTO;
import org.example.Entities.DTO.OrdenCompraPostDTO;
import org.example.Entities.OrdenCompra;
import org.example.Repositories.OrdenCompraRepository;
import org.example.Services.OrdenCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try{
            OrdenCompra ordenesCompras = service.findById(id);
            return ResponseEntity.ok(OrdenCompraDTO.fromEntity(ordenesCompras));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/post")
    public ResponseEntity<OrdenCompraPostDTO> crearOrdenCompra(@RequestBody OrdenCompra ordenCompraDTO) throws Exception {


        OrdenCompra nuevaOrden = service.save(ordenCompraDTO);
        OrdenCompraPostDTO respuestaDTO = OrdenCompraPostDTO.fromEntity(nuevaOrden);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
    }

}
