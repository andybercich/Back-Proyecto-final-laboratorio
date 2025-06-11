package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.Direccion;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DireccionDTO {

    private Long id;
    private String localidad;
    private String pais;
    private String provincia;
    private String departamento;
    private String codigoPostal;

    public static DireccionDTO fromEntity(Direccion direccion) {
        if (direccion == null) return null;
        return new DireccionDTO(
                direccion.getId(),
                direccion.getLocalidad(),
                direccion.getPais(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getCodigoPostal()
        );
    }

    public static List<DireccionDTO> fromEntityList(List<Direccion> direcciones) {
        if (direcciones == null) return Collections.emptyList();
        return direcciones.stream()
                .map(DireccionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
