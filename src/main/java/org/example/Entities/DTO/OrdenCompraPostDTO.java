package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.OrdenCompra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class OrdenCompraPostDTO {

    private Long id;
    private boolean estado;
    private UsuarioDTO usuario;
    private DireccionDTO direccion;
    private boolean direccionUsuario;
    private BigDecimal total;
    private LocalDate fecha;

    // Conversión de entidad a DTO
    public static OrdenCompraPostDTO fromEntity(OrdenCompra ordenCompra) {
        if (ordenCompra == null) {
            return null;
        }
        return OrdenCompraPostDTO.builder()
                .id(ordenCompra.getId())
                .estado(ordenCompra.isEstado())
                .usuario(UsuarioDTO.fromEntity(ordenCompra.getUsuario()))
                .direccion(DireccionDTO.fromEntity(ordenCompra.getDireccion()))
                .direccionUsuario(ordenCompra.isDireccionUsuario())
                .total(ordenCompra.getTotal())
                .fecha(ordenCompra.getFecha())
                .build();
    }

    // Conversión de lista de entidades a lista de DTOs
    public static List<OrdenCompraPostDTO> fromEntities(List<OrdenCompra> ordenesCompra) {
        return ordenesCompra.stream()
                .map(OrdenCompraPostDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
