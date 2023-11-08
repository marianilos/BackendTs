package edu.kalum.kalummanagement.core.model.dtos;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class JornadaCreateDTO implements Serializable {
    @NotEmpty(message = "El campo no debe ser vacio")
    private String jornada;
}
