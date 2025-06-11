package org.example.Services;

import org.example.Entities.*;
import org.example.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrdenCompraService extends BaseService<OrdenCompra,Long, OrdenCompraRepository >{

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;


    @Override
    public OrdenCompra save(OrdenCompra newOrdenCompra){
        try{

            List<OrdenCompraDetalle> detallePedidos = newOrdenCompra.getDetalles();
            Usuario user = usuarioRepository.findByMail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            List<Direccion> direccions= user.getDirecciones();

            if (!direccions.contains(newOrdenCompra.getDireccion())){
                direccionService.saveToken(newOrdenCompra.getDireccion());
            }

            for (OrdenCompraDetalle d : detallePedidos){
                if (d.getCantidad() > d.getDetalle().getStock()){
                    throw new Exception("EL DETALLE NO PUEDE CREARSE PORQUE NO HAY MÁS STOCK DE ESTE PRODUCTO");
                }else{
                    Detalle producto = d.getDetalle();
                    producto.setStock(producto.getStock() - d.getCantidad());
                    detalleRepository.saveAndFlush(producto);
                }

                d.setDetalle(detalleRepository.getReferenceById(d.getDetalle().getId()));
                d.setOrdenCompra(newOrdenCompra);
                d.calcularSubtotal();

            }
            newOrdenCompra.setDetalles(detallePedidos);
            newOrdenCompra.calcularTotal();
            newOrdenCompra.setTime();
            newOrdenCompra.setUsuario(user);
            repository.save(newOrdenCompra);

            for (OrdenCompraDetalle d : detallePedidos){
                ordenCompraDetalleRepository.save(d);
            }

            return newOrdenCompra;
        }catch (Exception e){
            throw new RuntimeException("Error al crear orden compra: "+ e.getMessage());
        }



    }


}
