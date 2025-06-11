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

    @PostMapping("/post")
    public ResponseEntity<OrdenCompraPostDTO> crearOrdenCompra(@RequestBody OrdenCompraPostDTO ordenCompraDTO) throws Exception {
        // Convertir DTO a entidad
        OrdenCompra ordenCompra = convertirDtoAEntidad(ordenCompraDTO);

        // Guardar la entidad
        OrdenCompra nuevaOrden = service.save(ordenCompra);

        // Convertir entidad guardada a DTO para devolverla
        OrdenCompraPostDTO respuestaDTO = OrdenCompraPostDTO.fromEntity(nuevaOrden);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
    }

    // Método auxiliar para convertir DTO a entidad (simplificado)
    private OrdenCompra convertirDtoAEntidad(OrdenCompraPostDTO dto) {
        OrdenCompra orden = new OrdenCompra();
        orden.setId(dto.getId());
        orden.setEstado(dto.isEstado());
        orden.setDireccion(dto.getDireccion() != null ? dto.getDireccion().toEntity() : null);
        orden.setUsuario(dto.getUsuario() != null ? dto.getUsuario().toEntity() : null);
        orden.setDireccionUsuario(dto.isDireccionUsuario());
        orden.setTotal(dto.getTotal());
        orden.setFecha(dto.getFecha());
        return orden;
    }
}
