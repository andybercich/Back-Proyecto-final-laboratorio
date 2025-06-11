package org.example.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Entities.DTO.DireccionDTO;
import org.example.Entities.Enum.Rol;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String nombre;
    private String dni;
    private String mail;
    private Rol rol;
    private boolean estado;
    private List<DireccionDTO> direcciones;
    private String token;

}
