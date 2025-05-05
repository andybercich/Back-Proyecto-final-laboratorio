package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Detalle")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Detalle extends Base{

    @ManyToOne
    @NotNull(message = "Ingresa un talle valido para el detalle")
    @JoinColumn(name = "talle_id")
    private Talle talle;

    private boolean estado;

    @NotBlank(message = "Si ingresas un color debe ser valido")
    private String color;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @NotNull(message = "Debes ingresar un producto valido para el detalle")
    private Producto producto;

    @Builder.Default
    @OneToMany(mappedBy = "detalle", orphanRemoval = true)
    @JsonManagedReference
    private List<Imagen> imagenList = new ArrayList<>();

    @NotNull(message = "Ingresa un stock para el producto")
    @Min(value = 0, message = "El valor minimo del stock producto es 0(cero)")
    private int stock;


}
