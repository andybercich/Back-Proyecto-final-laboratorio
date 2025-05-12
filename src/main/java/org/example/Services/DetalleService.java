package org.example.Services;

import org.example.Entities.Detalle;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DetalleService extends BaseService<Detalle, Long, DetalleRepository> {

    public List<Detalle> findAllDetalleByIdProducto (Long id){
        try{
            List<Detalle> detalles= repository.findByProductoId(id);
            return detalles;


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
