package org.example.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;

@Entity
@Table(name = "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Producto extends Base{

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Ingresa una categoria valida para el producto")
    private Categoria categoria;

    @NotNull(message = "Ingresa un nombre valido para el producto")
    @NotBlank(message = "Ingresa un nombre valido para el producto")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingresa un tipo producto valido para el producto")
    private TipoProducto tipoProducto;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingresa un sexo valido para el producto")
    private Sexo sexo;

}
