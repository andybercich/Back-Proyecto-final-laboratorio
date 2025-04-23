package org.example.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Direccion")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Direccion extends Base{

    private String localidad;

    private String pais;

    private String provincia;

    private String departamento;

    private String codigoPostal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarioDireccion",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "direccion_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();

}
