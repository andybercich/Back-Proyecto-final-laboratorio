package org.example.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.Direccion;

@Data
@Builder
@AllArgsConstructor
public class DireccionDTO {

    private Long id;
    private String localidad;
    private String pais;
    private String provincia;
    private String departamento;
    private String codigoPostal;

    public static DireccionDTO fromEntity(Direccion direccion) {
        return DireccionDTO.builder()
                .id(direccion.getId())
                .localidad(direccion.getLocalidad())
                .pais(direccion.getPais())
                .provincia(direccion.getProvincia())
                .departamento(direccion.getDepartamento())
                .codigoPostal(direccion.getCodigoPostal())
                .build();
    }
}
