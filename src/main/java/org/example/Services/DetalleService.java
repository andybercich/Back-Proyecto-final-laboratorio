package org.example.Services;

import jakarta.transaction.Transactional;
import org.example.Entities.Detalle;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DetalleService extends BaseService<Detalle, Long, DetalleRepository> {
    @Override
    @Transactional
    public Detalle update(Long id, Detalle nuevoDetalle) throws Exception {
        try {
            Detalle detalleExistente = repository.findById(id)
                    .orElseThrow(() -> new Exception("Detalle no encontrado con ID: " + id));

            // Si la nueva lista de imágenes está vacía, limpiamos la existente
            if (nuevoDetalle.getImagenList() == null || nuevoDetalle.getImagenList().isEmpty()) {
                detalleExistente.getImagenList().clear();
            } else {
                // Reemplaza completamente la lista (esto eliminará las viejas si orphanRemoval está activo)
                detalleExistente.getImagenList().clear();
                detalleExistente.getImagenList().addAll(nuevoDetalle.getImagenList());
            }

            // Copiamos los demás atributos necesarios
            detalleExistente.setColor(nuevoDetalle.getColor());
            detalleExistente.setStock(nuevoDetalle.getStock());
            detalleExistente.setTalle(nuevoDetalle.getTalle());
            detalleExistente.setProducto(nuevoDetalle.getProducto());
            detalleExistente.setEstado(nuevoDetalle.isEstado());

            return repository.saveAndFlush(detalleExistente);

        } catch (Exception e) {
            throw new Exception("Error al actualizar Detalle: " + e.getMessage(), e);
        }
    }
}
