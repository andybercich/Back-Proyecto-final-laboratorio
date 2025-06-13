package org.example.Controllers;

import org.example.Entities.AuthResponse;
import org.example.Entities.DTO.UsuarioDTO;
import org.example.Entities.UserLogin;
import org.example.Entities.Usuario;
import org.example.Repositories.UsuarioRepository;
import org.example.JWT.AuthService;
import org.example.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sneaks/usuario")
public class UsuarioController extends BaseController<Usuario,Long, UsuarioRepository, UsuarioService> {
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

    @PostMapping("/admin")
    public ResponseEntity<?> postAdmin(@RequestBody UsuarioDTO dto){
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getNombre());
            usuario.setMail(dto.getMail());
            usuario.setDni(dto.getDni());
            usuario.setPassword(dto.getPassword());
            usuario.setRol(dto.getRol()); // <-- aquí se respeta
            usuario.setEstado(dto.isEstado());

            AuthResponse usuario1 = service.postUsuarioAdmin(usuario);
            return ResponseEntity.ok(usuario1);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin(){
        try {
            List<Usuario> usuario1 = service.getAdmins();
            return ResponseEntity.ok(UsuarioDTO.fromEntitys(usuario1));
        }catch (Exception e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
        try {
            AuthResponse authResponse = authService.login(userLogin);
            if (!authResponse.isEstado()){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(authService.login(userLogin));
            }

        } catch (Exception e) {
            return ResponseEntity.status(404).body("DATOS INVALIDOS: " + e.getMessage());
        }
    }

    @PostMapping("/registrarUsuario")
    public ResponseEntity<AuthResponse> registrarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registrar(usuario));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario updatedUsuario = service.update(id, usuario);
            if (updatedUsuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(UsuarioDTO.fromEntity(updatedUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
