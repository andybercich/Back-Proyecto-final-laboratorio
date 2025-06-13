package org.example.Services;

import org.example.Entities.*;
import org.example.Entities.Enum.Rol;
import org.example.JWT.AuthService;
import org.example.JWT.JwtService;
import org.example.Repositories.DireccionRepository;
import org.example.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService extends BaseService<Usuario,Long, UsuarioRepository>{

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService serviceJWT;

    @Autowired
    private DireccionRepository direccionRepository;

    public AuthResponse postUsuarioAdmin(Usuario usuario){
        try {
            Usuario user = repository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (user.getRol() == Rol.ADMIN){

                return authService.registrar(usuario);


            }else{
                throw new RuntimeException("USUARIO NO AUTORIZADO PARA CREAR ADMINS");
            }


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Usuario> getAdmins(){
        try {
            Usuario user = repository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (user.getRol() == Rol.ADMIN){

                return repository.findAllByRol(Rol.ADMIN).stream()
                        .filter(u -> !u.getMail().equalsIgnoreCase(user.getMail()))
                        .collect(Collectors.toList());

            }else{
                throw new RuntimeException("USUARIO NO AUTORIZADO PARA CREAR ADMINS");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Usuario postUsuario(Usuario usuario){
        try{


            Direccion direccionNueva;
            List<Direccion> direccionList = usuario.getDirecciones();
            List<Direccion> direccionesNuevas = new ArrayList<>();
            List<Usuario> usuariosDirecciones;
            System.out.println(direccionList);


            for (Direccion direccion : direccionList){
                if (direccion.getId() == null){
                    usuariosDirecciones = direccion.getUsuarios();
                    usuariosDirecciones.add(usuario);
                    direccion.setUsuarios(usuariosDirecciones);
                    direccionRepository.save(direccion);
                    direccionesNuevas.add(direccion);
                }else if(direccion.getId() != null){

                    direccionNueva = direccionRepository.findById(direccion.getId()).orElseThrow();
                    direccionRepository.saveAndFlush(direccionNueva);
                    direccionesNuevas.add(direccionNueva);
                }

            }
            usuario.setDirecciones(direccionesNuevas);
            System.out.println(direccionList);
            System.out.println(direccionesNuevas);
            System.out.println(usuario);
            repository.save(usuario);
            return usuario;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public UpdateUser update (Long id, Usuario newUsuario, String token){

        try{

            String mail = serviceJWT.getMailFromToken(token);
            Usuario usuario = repository.findByMail(mail).orElseThrow();

            this.update(usuario.getId(), newUsuario);
            return new UpdateUser(usuario.getNombre(), usuario.getDni(), usuario.getMail(), usuario.getDirecciones());

        }catch (Exception e){
            throw new RuntimeException("El mail enviado es inválido para modificar este usuario: "+e.getMessage());
        }

    }

}
