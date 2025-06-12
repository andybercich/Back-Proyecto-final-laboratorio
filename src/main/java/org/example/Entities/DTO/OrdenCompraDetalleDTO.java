package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.OrdenCompraDetalle;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class OrdenCompraDetalleDTO {
    private Long id;
    private boolean estado;
    private DetalleDTO detalle;
    private int cantidad;

    public static OrdenCompraDetalleDTO fromEntity(OrdenCompraDetalle ordenCompraDetalle){
        return OrdenCompraDetalleDTO.builder()
                .id(ordenCompraDetalle.getId())
                .estado(ordenCompraDetalle.isEstado())
                .detalle(DetalleDTO.fromEntity(ordenCompraDetalle.getDetalle()))
                .cantidad(ordenCompraDetalle.getCantidad())
                .build();
    }

    public static List<OrdenCompraDetalleDTO> fromEntities(List<OrdenCompraDetalle> ordenesComprasDetalles) {
        return ordenesComprasDetalles.stream().map(OrdenCompraDetalleDTO::fromEntity).collect(Collectors.toList());
    }
}
