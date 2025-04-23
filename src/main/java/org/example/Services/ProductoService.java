package org.example.Services;

import org.example.Entities.Producto;
import org.example.Repositories.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends BaseService<Producto,Long, ProductoRepository>{
}
