package org.example.Services;

import org.example.Entities.Direccion;
import org.example.Entities.Usuario;
import org.example.Repositories.DireccionRepository;
import org.example.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService extends BaseService<Usuario,Long, UsuarioRepository>{

    @Autowired
    private DireccionRepository direccionRepository;

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

}
