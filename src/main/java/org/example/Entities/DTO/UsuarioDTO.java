package org.example.Entities.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.Usuario;
import org.example.Entities.Enum.TipoUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String contra;
    private String mail;

    @Enumerated(EnumType.STRING)
    private TipoUsuario rol;

    private String dni;

    @Builder.Default
    private List<DireccionDTO> direcciones = new ArrayList<>();

    public static UsuarioDTO fromEntity(Usuario usuario){
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .contra(usuario.getContra())
                .mail(usuario.getMail())
                .rol(usuario.getRol())
                .dni(usuario.getDni())
                .direcciones(
                        usuario.getDirecciones().stream()
                                .map(DireccionDTO::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static List<UsuarioDTO> fromEntitys(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::fromEntity).collect(Collectors.toList());
    }
}
