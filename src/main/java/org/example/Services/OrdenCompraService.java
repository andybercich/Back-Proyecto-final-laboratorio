package org.example.Services;

import jakarta.transaction.Transactional;
import org.example.Entities.*;
import org.example.Entities.DTO.DetalleDTO;
import org.example.Entities.DTO.OrdenCompraDTO;
import org.example.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrdenCompraService extends BaseService<OrdenCompra, Long, OrdenCompraRepository> {

    @Autowired
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private OrdenCompraDetalleService ordenCompraDetalleService;

    @Autowired
    private DireccionService direccionService;

    @Override
    @Transactional
    public OrdenCompra save(OrdenCompra orden) {
        try {
            Usuario user = usuarioRepository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            orden.setUsuario(user);

            if (!orden.isDireccionUsuario()) {
                direccionService.saveToken(orden.getDireccion());
            } else {
                boolean direccionValida = user.getDirecciones().stream()
                        .anyMatch(d -> d.getId().equals(orden.getDireccion().getId()));
                if (!direccionValida) {
                    throw new RuntimeException("La dirección no pertenece al usuario");
                }
            }
            System.out.println(orden.getDetalles());
            for (OrdenCompraDetalle d : orden.getDetalles()) {
                Detalle detalle = detalleRepository.getReferenceById(d.getDetalle().getId());

                if (detalle.getStock() < d.getCantidad()) {
                    throw new RuntimeException("No hay stock suficiente para el producto: " + detalle.getProducto().getNombre());
                }else{
                    detalle.setStock(detalle.getStock()-d.getCantidad());
                    System.out.println(DetalleDTO.fromEntity(detalle));
                    detalleRepository.saveAndFlush(detalle);
                }

                d.setDetalle(detalle);
                d.setOrdenCompra(orden);
                d.calcularSubtotal();
            }

            orden.setUsuario(user);
            orden.setTime();
            orden.calcularTotal();

            OrdenCompra ordenGuardada = repository.save(orden);
            System.out.println(OrdenCompraDTO.fromEntity(orden));
            for (OrdenCompraDetalle d : orden.getDetalles()) {
                Detalle detalle = d.getDetalle();
                detalle.setStock(detalle.getStock() - d.getCantidad());
                detalleRepository.save(detalle);

            }

            return ordenGuardada;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la orden de compra: " + e.getMessage(), e);
        }
    }

    @Transactional
    public List<OrdenCompra> getOrdenesUser(){
        try {
            Usuario user = usuarioRepository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return repository.findByUsuarioId(user.getId());


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    @Transactional
    public OrdenCompra update(Long id, OrdenCompra actualizada) {
        try {
            OrdenCompra existente = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada con ID: " + id));
            List<OrdenCompraDetalle> detallesDB = ordenCompraDetalleRepository.findByOrdenCompraId(id);

            for (OrdenCompraDetalle viejo : detallesDB) {
                Detalle detalle = viejo.getDetalle();
                detalle.setStock(detalle.getStock() + viejo.getCantidad());
                detalleRepository.save(detalle);
            }
            ordenCompraDetalleRepository.deleteAll(detallesDB);

            for (OrdenCompraDetalle nuevo : actualizada.getDetalles()) {
                Detalle detalle = detalleRepository.getReferenceById(nuevo.getDetalle().getId());

                if (detalle.getStock() < nuevo.getCantidad()) {
                    throw new RuntimeException("No hay stock suficiente para el producto: " + detalle.getProducto().getNombre());
                }

                nuevo.setDetalle(detalle);
                nuevo.setOrdenCompra(existente);
                nuevo.calcularSubtotal();

                detalle.setStock(detalle.getStock() - nuevo.getCantidad());
                detalleRepository.save(detalle);

                ordenCompraDetalleService.save(nuevo);
            }

            if (!actualizada.isDireccionUsuario()){

                existente.setDireccion(direccionService.saveAdmin(actualizada.getDireccion(),
                        actualizada.getUsuario().getId()));
                existente.setDireccionUsuario(false);
            }else{
                existente.setDireccion(existente.getDireccion());
                existente.setDireccionUsuario(true);
            }

            existente.setDetalles(actualizada.getDetalles());
            existente.setTime();
            existente.calcularTotal();

            return repository.saveAndFlush(existente);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la orden de compra: " + e.getMessage(), e);
        }
    }

}

