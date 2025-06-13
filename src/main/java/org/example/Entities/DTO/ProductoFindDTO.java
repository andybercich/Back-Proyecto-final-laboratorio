package org.example.Entities.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.Categoria;
import org.example.Entities.Detalle;
import org.example.Entities.Enum.Sexo;
import org.example.Entities.Enum.TipoProducto;
import org.example.Entities.Producto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProductoFindDTO {

    private Long id;
    private Categoria categoria;
    private String nombre;
    private TipoProducto tipoProducto;
    private Sexo sexo;
    private List<DetalleFindDTO> detalles;

       public static ProductoFindDTO fromEntity(Producto producto, List<Detalle> detalles) {
            return new ProductoFindDTO(
                    producto.getId(),
                    producto.getCategoria(),
                    producto.getNombre(),
                    producto.getTipoProducto(),
                    producto.getSexo(),
                    DetalleFindDTO.fromEntitys(detalles)
            );}

}
