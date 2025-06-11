package org.example.Entities.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.Entities.Enum.Rol;
import org.example.Entities.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String password;
    private String mail;
    private Rol rol;

    private String dni;

    @Builder.Default
    private List<DireccionDTO> direcciones = new ArrayList<>();

    public static UsuarioDTO fromEntity(Usuario usuario){
        if(usuario == null) return null;
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .password(usuario.getPassword())
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
        if(usuarios == null) return new ArrayList<>();
        return usuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNombre(this.nombre);
        usuario.setPassword(this.password);
        usuario.setMail(this.mail);
        usuario.setRol(this.rol);
        usuario.setDni(this.dni);

        if (this.direcciones != null) {
            usuario.setDirecciones(
                    this.direcciones.stream()
                            .map(DireccionDTO::toEntity)
                            .collect(Collectors.toList())
            );
        } else {
            usuario.setDirecciones(new ArrayList<>());
        }

        return usuario;
    }
}
