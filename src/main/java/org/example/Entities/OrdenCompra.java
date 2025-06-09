package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "OrdenCompra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class OrdenCompra extends Base{

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "La orden compra debe estar relacionada con un usuario")
    private Usuario usuario;

    private BigDecimal total;

    private double descuento;

    private LocalDate fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "direccion_id", nullable = false)
    @NotNull(message = "Debe especificar una dirección válida")
    private Direccion direccion;

    @NotNull(message = "Determina si se usará la direccion del usuario o no")
    private boolean direccionUsuario;

}
