package org.example.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "OrdenCompraDetalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class OrdenCompraDetalle extends Base{

    @ManyToOne
    @JoinColumn(name = "ordenCompra_id")
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @NotNull(message = "Ingresa un producto válido")
    private Producto producto;

    private int cantidad;

}
