package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.OrdenCompra;
import org.example.Entities.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class OrdenCompraDTO {
    private Long id;
    private boolean estado;
    private UsuarioDTO usuario;
    private BigDecimal total;
    private double descuento;
    private LocalDate fecha;
    private DireccionDTO direccion;
    private boolean direccionUsuario;

    public static OrdenCompraDTO fromEntity(OrdenCompra ordenCompra) {
        return OrdenCompraDTO.builder()
                .id(ordenCompra.getId())
                .estado(ordenCompra.isEstado())
                .usuario(UsuarioDTO.fromEntity(ordenCompra.getUsuario()))
                .total(ordenCompra.getTotal())
                .fecha(ordenCompra.getFecha())
                .direccion(DireccionDTO.fromEntity(ordenCompra.getDireccion()))
                .direccionUsuario(ordenCompra.isDireccionUsuario())
                .build();
    }

    public static List<OrdenCompraDTO> fromEntities(List<OrdenCompra> ordenesCompra) {
        return ordenesCompra.stream().map(OrdenCompraDTO::fromEntity).collect(Collectors.toList());
    }
}
