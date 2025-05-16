package org.example.Services;

import org.example.Entities.Direccion;
import org.example.Repositories.DireccionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DireccionService extends BaseService<Direccion, Long, DireccionRepository> {
    @Override
    public Direccion update(Long id, Direccion entity) throws Exception {
        try {
            Direccion existingEntity = repository.findById(id)
                    .orElseThrow(() -> new Exception("Entity not found"));

            // Ignoro "usuarios" al hacer el copyProperties SOLO para Direccion
            BeanUtils.copyProperties(entity, existingEntity, "id", "usuarios");

            return repository.saveAndFlush(existingEntity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
