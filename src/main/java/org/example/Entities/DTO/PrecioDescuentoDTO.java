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
public class PrecioDescuentoDTO {
    private Long id;
    private boolean estado;
    private Descuento descuento;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private DetalleDescuentoDTO detalleDescuentoDTO;

    public static PrecioDescuentoDTO fromEntity(Precio precio){
        return new PrecioDescuentoDTO(precio.getId(), precio.isEstado(), precio.getDescuento(), precio.getPrecioCompra(), precio.getPrecioVenta(), DetalleDescuentoDTO.fromEntity(precio.getDetalle()));
    }

    public static List<PrecioDescuentoDTO> fromEntitys(List<Precio> precios) {
        List<PrecioDescuentoDTO> preciosDescuentosDTOS = new ArrayList<>();
        for (Precio precio : precios) {
            preciosDescuentosDTOS.add(fromEntity(precio));
        }
        return preciosDescuentosDTOS;
    }
}
