package org.example.Services;

import org.example.Entities.DTO.ProductoFindDTO;
import org.example.Entities.Detalle;
import org.example.Entities.Producto;
import org.example.Repositories.DetalleRepository;
import org.example.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService extends BaseService<Producto,Long, ProductoRepository>{

    @Autowired
    private DetalleRepository detalleRepository;

    public List<ProductoFindDTO> findProductoConDetalles(String param) {
        try {
            String[] palabras = param.trim().toLowerCase().split("\\s+");
            String palabra1 = palabras.length > 0 ? palabras[0] : "";
            String palabra2 = palabras.length > 1 ? palabras[1] : "";

            List<Producto> productos = repository.buscarPorNombreAvanzado(param, palabra1, palabra2);
            List<ProductoFindDTO> productoFindDTOS = new ArrayList<>();

            for (Producto p : productos){
                productoFindDTOS.add( ProductoFindDTO.fromEntity(p, detalleRepository.findByProductoId(p.getId()) ));

            }

            return productoFindDTOS;

        } catch (Exception e) {
            throw new RuntimeException("No se pudo realizar la búsqueda del producto con detalles: " + e.getMessage(), e);
        }
    }

}
