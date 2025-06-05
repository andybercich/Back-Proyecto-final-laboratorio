package org.example.Controllers;

import org.example.Entities.AuthResponse;
import org.example.Entities.UserLogin;
import org.example.Entities.Usuario;
import org.example.Repositories.UsuarioRepository;
import org.example.JWT.AuthService;
import org.example.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sneaks/usuario")
public class UsuarioController extends BaseController<Usuario,Long, UsuarioRepository, UsuarioService>{
    @Autowired
    private AuthService authService;
    public UsuarioController(UsuarioService service) {
        super(service);
    }
/*
    @PostMapping("/post")
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario){
        try {
            Usuario user = service.postUsuario(usuario);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
        try {
            return ResponseEntity.ok(authService.login(userLogin));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("DATOS INVALIDOS: " + e.getMessage());
        }
    }

    @PostMapping("/registrarUsuario")
    public ResponseEntity<AuthResponse> registrarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registrar(usuario));
    }

}
