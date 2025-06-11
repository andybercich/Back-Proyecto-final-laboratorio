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
            return repository.save(detalle);

        }catch (Exception e){
            throw new RuntimeException("No se pudo crear el nuevo detalle: "+e.getMessage());
        }
    }

    @Transactional
    public Detalle updateDetalle(Detalle detalle, Long id) {
        Detalle detalleOld = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        detalleOld.setColor(detalle.getColor());
        detalleOld.setEstado(detalle.isEstado());
        detalleOld.setStock(detalle.getStock());

        Talle talle = talleRepository.findById(detalle.getTalle().getId())
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
        detalleOld.setTalle(talle);

        Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        detalleOld.setProducto(producto);

        List<Imagen> nuevasImagenes = new ArrayList<>();
        for (Imagen imagen : detalle.getImagenList()) {
            Imagen img;
            if (imagen.getId() != null) {
                img = detalleOld.getImagenList().stream()
                        .filter(i -> i.getId().equals(imagen.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Imagen no encontrada: id=" + imagen.getId()));
                img.setUrl(imagen.getUrl());
                img.setAlt(imagen.getAlt());
            } else {
                img = new Imagen();
                img.setUrl(imagen.getUrl());
                img.setAlt(imagen.getAlt());
                img.setDetalle(detalleOld);
            }
            nuevasImagenes.add(img);
        }
        detalleOld.getImagenList().clear();
        detalleOld.getImagenList().addAll(nuevasImagenes);

        if (detalle.getPrecio() != null) {
            Precio nuevoPrecio = new Precio();
            nuevoPrecio.setPrecioCompra(detalle.getPrecio().getPrecioCompra());
            nuevoPrecio.setPrecioVenta(detalle.getPrecio().getPrecioVenta());

            if (detalle.getPrecio().getDescuento() != null &&
                    detalle.getPrecio().getDescuento().getId() != null) {

                Descuento descuento = descuentoRepository.findById(
                        detalle.getPrecio().getDescuento().getId()
                ).orElseThrow(() -> new RuntimeException("Descuento no encontrado"));

                nuevoPrecio.setDescuento(descuento);
            }

            nuevoPrecio.setDetalle(detalleOld);
            detalleOld.setPrecio(nuevoPrecio);
        }

        return repository.save(detalleOld);
    }



}
