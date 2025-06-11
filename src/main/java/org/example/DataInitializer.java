package org.example;


import jakarta.persistence.EntityManager;
import org.example.Entities.*;
import org.example.Entities.Enum.Rol;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Crear categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Ropa deportiva");
        entityManager.persist(categoria);

        // Crear producto
        Producto producto = new Producto();
        producto.setCategoria(categoria);
        producto.setNombre("Zapatilla runner");
        producto.setTipoProducto(TipoProducto.CALZADO);
        producto.setSexo(Sexo.Masculino);
        entityManager.persist(producto);

        // Crear talle
        Talle talle = new Talle();
        talle.setTalle("42");
        entityManager.persist(talle);

        // Crear descuento
        Descuento descuento = new Descuento();
        descuento.setFechaInicio(LocalDate.now().minusDays(15));
        descuento.setFechaFin(LocalDate.now().plusDays(10));
        descuento.setDescuento(0.15);
        entityManager.persist(descuento);



        //  Crear Precio
        Precio precio = new Precio();
        precio.setDescuento(descuento);
        precio.setPrecioCompra(BigDecimal.valueOf(10000));
        precio.setPrecioVenta(BigDecimal.valueOf(13000));

        //  Crear Detalle
        Detalle detalle = new Detalle();
        detalle.setColor("Negro");
        detalle.setEstado(true);
        detalle.setProducto(producto);
        detalle.setTalle(talle);
        detalle.setStock(50);
        detalle.setPrecio(precio);
        precio.setDetalle(detalle);

        entityManager.persist(detalle);

        Imagen imagen = new Imagen();
        imagen.setUrl("https://mi-ecommerce.com/imagenes/zapatilla-runner.jpg");
        imagen.setAlt("Zapatilla negra runner");
        imagen.setDetalle(detalle);
        entityManager.persist(imagen);

        detalle.setImagenList(List.of(imagen));

        //  Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setPassword(passwordEncoder.encode("password123"));
        usuario.setDni("12345678");
        usuario.setMail("juan@example.com");
        usuario.setRol(Rol.USER);
        entityManager.persist(usuario);

        //  Crear Dirección
        Direccion direccion = new Direccion();
        direccion.setLocalidad("Buenos Aires");
        direccion.setProvincia("Buenos Aires");
        direccion.setPais("Argentina");
        direccion.setDepartamento("La Matanza");
        direccion.setCodigoPostal("1754");
        direccion.setUsuarios(List.of(usuario));
        entityManager.persist(direccion);
        usuario.setDirecciones(List.of(direccion));

        // Crear OrdenCompra
        OrdenCompra orden = new OrdenCompra();
        orden.setUsuario(usuario);
        orden.setDireccion(direccion);
        orden.setDireccionUsuario(true);
        orden.setFecha(LocalDate.now());
        orden.setDescuento(0);
        orden.setTotal(BigDecimal.valueOf(26000));
        entityManager.persist(orden);

        // Crear OrdenCompraDetalle
        OrdenCompraDetalle detalleOrden = new OrdenCompraDetalle();
        detalleOrden.setOrdenCompra(orden);
        detalleOrden.setDetalle(detalle);
        detalleOrden.setCantidad(2);
        detalleOrden.setSubtotal(BigDecimal.valueOf(13000 * 2));
        entityManager.persist(detalleOrden);

        orden.setDetalles(List.of(detalleOrden));
    }
}
