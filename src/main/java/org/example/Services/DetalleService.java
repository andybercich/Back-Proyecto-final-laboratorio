package org.example.Services;

import org.example.Entities.DTO.DetalleDTO;
import org.example.Entities.Detalle;
import org.example.Entities.Imagen;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.example.Repositories.PrecioRepository;
import org.example.Repositories.TalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetalleService extends BaseService<Detalle, Long, DetalleRepository> {
    @Autowired
    private TalleRepository talleRepository;

    @Autowired
    private PrecioRepository precioRepository;

    public List<Detalle> findAllDetalleByIdProducto (Long id){
        try{
            List<Detalle> detalles= repository.findByProductoId(id);
            return detalles;


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Detalle save(Detalle detalle){
        try{

            Precio precio = detalle.getPrecio();

            precio.setDetalle(detalle);
            precioRepository.save(precio);
            detalle.setPrecio(precio);

            return repository.save(detalle);



        }catch (Exception e){
            throw new RuntimeException("No se pudo crear el nuevo detalle: "+e.getMessage());
        }
    }

    @Transactional
    public Detalle updateDetalle(Detalle detalle) {
        Detalle detalleOld = repository.findById(detalle.getId())
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        detalleOld.setColor(detalle.getColor());
        detalleOld.setEstado(detalle.isEstado());
        detalleOld.setStock(detalle.getStock());
        detalleOld.setTalle(talleRepository.findById(detalle.getTalle().getId())
                .orElseThrow(() -> new RuntimeException("Talle no encontrado")));

        List<Imagen> nuevasImagenes = new ArrayList<>();

        for (Imagen imagenes : detalle.getImagenList()) {
            Imagen img;
            if ( imagenes.getId() != null) {
                img = detalle.getImagenList().stream()
                        .filter(i -> i.getId().equals(imagenes.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Imagen no encontrada: id=" + imagenes.getId()));
                img.setUrl(imagenes.getUrl());
                img.setAlt(imagenes.getAlt());
            } else {
                img = new Imagen();
                img.setUrl(imagenes.getUrl());
                img.setAlt(imagenes.getAlt());
                img.setDetalle(detalleOld);
            }
            nuevasImagenes.add(img);
        }

        detalleOld.getImagenList().clear();
        detalleOld.getImagenList().addAll(nuevasImagenes);
        repository.save(detalleOld);

        return repository.save(detalleOld);
    }

}
