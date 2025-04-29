package org.example.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Entities.Enum.TipoUsuario;

import java.util.ArrayList;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Usuario extends Base{

    private String nombre;

    private String contra;

    @Enumerated(EnumType.STRING)
    private TipoUsuario rol;

    private String dni;

    private String mail;

    @Builder.Default
    @ManyToMany(mappedBy = "usuarios")
    private ArrayList<Direccion> direcciones = new ArrayList<>();

}
