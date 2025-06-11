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
}
