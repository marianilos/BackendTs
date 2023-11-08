package edu.kalum.kalummanagement.core.model.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarreraTecnicaCreateDTO implements Serializable {
    @NotEmpty(message = "El campo no debe de ser vacio")
    private String carreraTecnica;
}
