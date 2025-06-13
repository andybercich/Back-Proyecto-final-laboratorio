package org.example.Entities.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DetalleFindDTO {
    private Long id;

    private Talle talle;

    private boolean estado;

    private String color;

    private List<ImagenDTO> imagenList;

    private int stock;

    private PrecioDTO precio;

    public static DetalleFindDTO fromEntity(Detalle detalle){
        return new DetalleFindDTO(detalle.getId(), detalle.getTalle(), detalle.isEstado(),
                detalle.getColor(), ImagenDTO.fromEntitys(detalle.getImagenList()),
                detalle.getStock(), PrecioDTO.fromEntity(detalle.getPrecio()));
    }

    public static List<DetalleFindDTO> fromEntitys(List<Detalle> detalles) {
        List<DetalleFindDTO> detalleDTOS = new ArrayList<>();
        for (Detalle detalle : detalles) {
            detalleDTOS.add(fromEntity(detalle));
        }
        return detalleDTOS;
    }

}
