package org.example.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Imagen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Imagen extends Base{

    @Column()
    @NotNull(message = "Ingresa una url valida de la imagen")
    @NotBlank(message = "Ingresa una url valida de la imagen")
    private String url;

    @Column()
    private String alt;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "detalle_id")
    @JsonBackReference
    private Detalle detalle;

}
