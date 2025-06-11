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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "direccion_id", nullable = false)
    @NotNull(message = "Debe especificar una dirección válida")
    private Direccion direccion;

    @NotNull(message = "Determina si se usará la direccion del usuario o no")
    private boolean direccionUsuario;

    @OneToMany(mappedBy = "ordenCompra", orphanRemoval = true)
    protected List<OrdenCompraDetalle> detalles = new ArrayList<>();
    public void calcularTotal() {
        BigDecimal suma = BigDecimal.ZERO;
        for (OrdenCompraDetalle detalle : detalles) {
            detalle.calcularSubtotal();
            if (detalle.getSubtotal() != null) {
                suma = suma.add(detalle.getSubtotal());
            }
        }
        this.total = suma;
    }
    public void setTime (){
        this.fecha = LocalDate.now();
    }

}
