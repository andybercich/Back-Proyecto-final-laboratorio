package org.example.Services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.Entities.DTO.DetalleDTO;
import org.example.Entities.Detalle;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;
import org.example.Entities.Imagen;
import org.example.Entities.Precio;
import org.example.Repositories.DetalleRepository;
import org.example.Repositories.PrecioRepository;
import org.example.Repositories.TalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetalleService extends BaseService<Detalle, Long, DetalleRepository> {
    @PersistenceContext
    private EntityManager entityManager;

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

    public List<Detalle> findDetallesConDescuentoActivo (String fecha){
        try{

            LocalDate fechaParsed = LocalDate.parse(fecha);
            return repository.findDetallesConDescuentoActivo(fechaParsed);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<DetalleDTO> getDetallesUnicosConFiltros(
            Boolean estado, Sexo sexo, TipoProducto tipo,
            Long idTalle, String categoria, String fechaDescuento,
            BigDecimal precioMin, BigDecimal precioMax
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Detalle> cq = cb.createQuery(Detalle.class);
        Root<Detalle> detalle = cq.from(Detalle.class);
        Join<Object, Object> producto = detalle.join("producto");
        Join<Object, Object> precio = detalle.join("precio", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (estado != null)
            predicates.add(cb.equal(detalle.get("estado"), estado));

        if (sexo != null)
            predicates.add(cb.equal(producto.get("sexo"), sexo));

        if (tipo != null)
            predicates.add(cb.equal(producto.get("tipoProducto"), tipo));

        if (idTalle != null)
            predicates.add(cb.equal(detalle.get("talle").get("id"), idTalle));

        if (categoria != null)
            predicates.add(cb.equal(producto.get("categoria"), categoria));

        if (fechaDescuento != null) {
            LocalDate fecha = LocalDate.parse(fechaDescuento);
            predicates.add(cb.and(
                    cb.isNotNull(precio.get("descuento")),
                    cb.lessThanOrEqualTo(precio.get("descuento").get("fechaInicio"), fecha),
                    cb.greaterThanOrEqualTo(precio.get("descuento").get("fechaFin"), fecha)
            ));
        }

        if (precioMin != null)
            predicates.add(cb.greaterThanOrEqualTo(precio.get("precioVenta"), precioMin));

        if (precioMax != null)
            predicates.add(cb.lessThanOrEqualTo(precio.get("precioVenta"), precioMax));

        cq.select(detalle).where(predicates.toArray(new Predicate[0]));

        // Agrupar por producto.id para que haya un solo detalle por producto
        cq.groupBy(detalle.get("producto").get("id"));

        return entityManager.createQuery(cq).getResultList()
                .stream()
                .map(DetalleDTO::fromEntity)
                .toList();
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
