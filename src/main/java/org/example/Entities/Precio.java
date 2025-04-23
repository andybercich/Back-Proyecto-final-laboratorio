package org.example.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Entity
@Table(name = "Precio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Precio extends Base{

    @ManyToOne
    @JoinColumn(name = "descuento_id")
    private Descuento descuento;


    private BigDecimal precioCompra;

    @NotNull(message = "Ingresa un precio venta del producto")
    private BigDecimal precioVenta;

    @NotNull(message = "Ingresa un detalle valido")
    private Detalle detalle;

}
