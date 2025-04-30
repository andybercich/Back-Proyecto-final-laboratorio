package org.example.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.Entities.Enum.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "usuarios")
    private List<Direccion> direcciones = new ArrayList<>();

}
