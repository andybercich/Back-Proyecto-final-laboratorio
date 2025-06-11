package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.Descuento;
import org.example.Entities.Detalle;
import org.example.Entities.Precio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PrecioDTO {
    private Long id;
    private boolean estado;
    private Descuento descuento;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;

    public static PrecioDTO fromEntity(Precio precio){
        return new PrecioDTO(precio.getId(), precio.isEstado(), precio.getDescuento(), precio.getPrecioCompra(), precio.getPrecioVenta());
    }

    public static List<PrecioDTO> fromEntitys(List<Precio> precios) {
        List<PrecioDTO> preciosDTOS = new ArrayList<>();
        for (Precio precio : precios) {
            preciosDTOS.add(fromEntity(precio));
        }
        return preciosDTOS;
    }
}
