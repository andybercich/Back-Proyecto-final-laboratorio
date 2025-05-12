package org.example.Controllers;

import org.apache.catalina.connector.Response;
import org.example.Entities.Usuario;
import org.example.Repositories.UsuarioRepository;
import org.example.Services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sneaks/usuario")
public class UsuarioController extends BaseController<Usuario,Long, UsuarioRepository, UsuarioService>{
    public UsuarioController(UsuarioService service) {
        super(service);
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
