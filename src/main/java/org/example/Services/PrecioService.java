package org.example.Services;

import org.example.Entities.Base;
import org.example.Entities.Precio;
import org.example.Repositories.PrecioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrecioService extends BaseService<Precio,Long, PrecioRepository> {
    public List<Precio> getDetalleDescuentoByFecha(LocalDate date){
        try {
            return  repository.findPreciosConDescuentoActivo(date);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
