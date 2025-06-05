package org.example.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.Entities.Enum.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"mail"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Usuario extends Base implements UserDetails {

    @NotNull(message = "Nombre no puede ser null ")
    @NotBlank(message = "Nombre no puede ser vacio ")
    private String nombre;

    @NotNull(message = "Contraseña no puede ser null ")
    @NotBlank(message = "Contraseña no puede ser vacio ")
    private String password;

    @NotNull(message = "El dni del usuario no puede ser null")
    @Column(unique = true)
    private String dni;

    @Column(nullable = false)
    @NotBlank(message = "No puede estar el mail vacio")
    private String mail;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToMany(mappedBy = "usuarios")
    private List<Direccion> direcciones = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }
}
