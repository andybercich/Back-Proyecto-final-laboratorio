package org.example.Services;

import org.example.Entities.Direccion;
import org.example.Entities.Usuario;
import org.example.JWT.JwtService;
import org.example.Repositories.DireccionRepository;
import org.example.Repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DireccionService extends BaseService<Direccion, Long, DireccionRepository> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Direccion update(Long id, Direccion entity) throws Exception {
        try {
            Direccion existingEntity = repository.findById(id)
                    .orElseThrow(() -> new Exception("Entity not found"));

            BeanUtils.copyProperties(entity, existingEntity, "id", "usuarios");

            return repository.saveAndFlush(existingEntity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Direccion saveToken(Direccion entity) {
        try {
            Usuario user = usuarioRepository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


            entity.setUsuarios(new ArrayList<>());
            entity.getUsuarios().add(user);

            user.getDirecciones().add(entity);

            repository.save(entity);
            usuarioRepository.save(user);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la dirección: " + e.getMessage());
        }
    }


}
