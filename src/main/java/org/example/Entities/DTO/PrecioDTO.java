package org.example.Entities.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Entities.Descuento;
import org.example.Entities.Precio;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrecioDTO {

    private Descuento descuento;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;

    public static PrecioDTO fromEntity(Precio precio) {
        if (precio == null) {
            return null;
        }

        PrecioDTO dto = new PrecioDTO();
        dto.setDescuento(precio.getDescuento());
        dto.setPrecioCompra(precio.getPrecioCompra());
        dto.setPrecioVenta(precio.getPrecioVenta());

        return dto;
    }
}
