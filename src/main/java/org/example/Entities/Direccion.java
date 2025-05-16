package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
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

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarioDireccion",
            joinColumns = @JoinColumn(name = "direccion_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario")
    )
    private List<Usuario> usuarios = new ArrayList<>();

}
