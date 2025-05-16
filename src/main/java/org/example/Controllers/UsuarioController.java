package org.example.Controllers;

import org.apache.catalina.connector.Response;
import org.example.Entities.DTO.UsuarioDTO;
import org.example.Entities.Usuario;
import org.example.Repositories.UsuarioRepository;
import org.example.Services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sneaks/usuario")
public class UsuarioController extends BaseController<Usuario,Long, UsuarioRepository, UsuarioService>{
    public UsuarioController(UsuarioService service) {
        super(service);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuario(){
        try{
            List<Usuario> usuarios = service.findAll();
            return ResponseEntity.ok(UsuarioDTO.fromEntitys(usuarios));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id){
        try{
            Usuario usuario = service.findById(id);
            return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/post")
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario){
        try {
            Usuario user = service.postUsuario(usuario);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
