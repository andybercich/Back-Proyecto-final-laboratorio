package org.example.Entities.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.Detalle;
import org.example.Entities.Producto;
import org.example.Entities.Talle;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DetalleDescuentoDTO {
    private Long id;

    private Talle talle;

    private boolean estado;

    private String color;

    private Producto producto;

    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenDTO> imagenList = new ArrayList<>();

    private int stock;

    public static DetalleDescuentoDTO fromEntity(Detalle detalle){
        return new DetalleDescuentoDTO(
                detalle.getId(),
                detalle.getTalle(),
                detalle.isEstado(),
                detalle.getColor(),
                detalle.getProducto(),
                ImagenDTO.fromEntitys(detalle.getImagenList()),
                detalle.getStock()
        );
    }
    public static List<DetalleDescuentoDTO> fromEntitys(List<Detalle> detalles) {
        List<DetalleDescuentoDTO> detalleDescuentoDTOS = new ArrayList<>();
        for (Detalle detalle : detalles) {
            detalleDescuentoDTOS.add(fromEntity(detalle));
        }
        return detalleDescuentoDTOS;
    }
}
