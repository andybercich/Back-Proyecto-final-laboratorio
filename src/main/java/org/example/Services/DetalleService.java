package org.example.Services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.Entities.*;
import org.example.Entities.DTO.DetalleDTO;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;
import org.example.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleService extends BaseService<Detalle, Long, DetalleRepository> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TalleRepository talleRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private DescuentoRepository descuentoRepository;

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

        return entityManager.createQuery(cq).getResultList().stream()
                .collect(Collectors.toMap(
                        d -> d.getProducto().getId(),  // clave: id de producto
                        d -> d,                        // valor: el primer detalle encontrado
                        (d1, d2) -> d1                // en caso de conflicto, conservar el primero
                ))
                .values()
                .stream()
                .map(DetalleDTO::fromEntity)
                .toList();
    }

    public Detalle save(Detalle detalle){
        try{

            Precio precio = detalle.getPrecio();
            precio.setDetalle(detalle);
            precioRepository.save(precio);
            return repository.save(detalle);

        }catch (Exception e){
            throw new RuntimeException("No se pudo crear el nuevo detalle: "+e.getMessage());
        }
    }

    @Transactional
    public Detalle updateDetalle(Detalle detalleActualizado, Long id) throws Exception {
        Detalle existente = repository.findById(id)
                .orElseThrow(() -> new Exception("Detalle no encontrado"));

        // Actualizás campos simples
        existente.setColor(detalleActualizado.getColor());
        existente.setEstado(detalleActualizado.isEstado());
        existente.setStock(detalleActualizado.getStock());
        existente.setTalle(detalleActualizado.getTalle());
        existente.setProducto(detalleActualizado.getProducto());

        // Imagenes: manejarlas con cuidado si usás cascade
        existente.getImagenList().clear();
        existente.getImagenList().addAll(detalleActualizado.getImagenList());

        // ✅ ACA ESTÁ LA CLAVE: actualizás el precio existente en lugar de setear uno nuevo
        Precio precioExistente = existente.getPrecio();
        Precio nuevoPrecio = detalleActualizado.getPrecio();

        if (nuevoPrecio != null) {
            precioExistente.setPrecioCompra(nuevoPrecio.getPrecioCompra());
            precioExistente.setPrecioVenta(nuevoPrecio.getPrecioVenta());
            precioExistente.setDescuento(nuevoPrecio.getDescuento());
        }

        return repository.save(existente);
    }
}
