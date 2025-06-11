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
public class DetalleDTO {
    private Long id;

    private Talle talle;

    private boolean estado;

    private String color;

    private Producto producto;

    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenDTO> imagenList = new ArrayList<>();

    private int stock;

    private PrecioDTO precioDTO;

    public static DetalleDTO fromEntity(Detalle detalle){
        return new DetalleDTO(detalle.getId(), detalle.getTalle(), detalle.isEstado(),
                detalle.getColor(), detalle.getProducto(), ImagenDTO.fromEntitys(detalle.getImagenList()),
                detalle.getStock(), PrecioDTO.fromEntity(detalle.getPrecio()));
    }

    public static List<DetalleDTO> fromEntitys(List<Detalle> detalles) {
        List<DetalleDTO> detalleDTOS = new ArrayList<>();
        for (Detalle detalle : detalles) {
            detalleDTOS.add(fromEntity(detalle));
        }
        return detalleDTOS;
    }

}
