package org.example.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "OrdenCompraDetalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class OrdenCompraDetalle extends Base{

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne(optional = false, cascade = { CascadeType.MERGE})
    @JoinColumn(name = "detalle_producto_id", nullable = false)
    @NotNull(message = "Ingresa un producto válido")
    private Detalle detalle;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private int cantidad;

}
