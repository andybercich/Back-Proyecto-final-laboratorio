package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
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

    @NotNull(message = "La orden compra debe estar relacionado con un usuario")
    private Usuario usuario;

    private BigDecimal total;

    private double descuento;

    private LocalDate fecha;

    private Direccion direccion;

    @NotNull(message = "Determina si se usará la direccion del usuario o no")
    private boolean direccionUsuario;

}
