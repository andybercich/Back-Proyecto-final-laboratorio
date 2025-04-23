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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Descuento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Descuento extends Base{

    @NotNull(message = "Ingresa una fecha inicio del descuento")
    private LocalDate fechaInicio;


    @NotNull(message = "Ingresa una fecha final del descuento")
    private LocalDate fechaFin;


    @NotNull(message = "Ingresa un porcentaje del descuento")
    private double descuento;

}
