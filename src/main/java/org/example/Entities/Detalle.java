package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenList = new ArrayList<>();

    @NotNull(message = "Ingresa un stock para el producto")
    @Min(value = 0, message = "El valor minimo del stock producto es 0(cero)")
    private int stock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "precio_id")
    @NotNull
    private Precio precio;

    public BigDecimal calcularTotal(){
        BigDecimal precioUnitario = this.precio.getPrecioVenta();
        Descuento descuento = getPrecio().getDescuento();

        if (descuento != null && descuento.isValid()) {
            BigDecimal porcentaje = BigDecimal.valueOf(descuento.getDescuento());
            BigDecimal descuentoAplicado = precioUnitario.multiply(porcentaje).divide(BigDecimal.valueOf(100));
            precioUnitario = precioUnitario.subtract(descuentoAplicado);

        }
        return precioUnitario;

    }
}
