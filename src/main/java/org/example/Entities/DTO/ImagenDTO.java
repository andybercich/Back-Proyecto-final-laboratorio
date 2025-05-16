package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.Imagen;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ImagenDTO {
    private Long id;
    private String alt;
    private String url;
    private Long idDetalle;

    static ImagenDTO fromEntity(Imagen imagen) {
        return new ImagenDTO(imagen.getId(), imagen.getAlt(), imagen.getUrl(), imagen.getDetalle().getId()
        );
    }

    static List<ImagenDTO> fromEntitys(List<Imagen> imagenes) {
        List<ImagenDTO> imagenesDTO = new ArrayList<>();
        for (Imagen img : imagenes) {
            imagenesDTO.add(fromEntity(img));
        }
        return imagenesDTO;
    }


}
