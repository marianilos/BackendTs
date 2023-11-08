package edu.kalum.kalummanagement.core.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AspiranteCreateDTO implements Serializable {
    ///NO SE INCLUYE ESTATUS PORQUE YA TIENE UN DEFAULT VALUE
    @NotEmpty(message = "El campo apellidos no puede ser vacio")
    private String apellidos;
    @NotEmpty(message = "El campo nombres no puede ser vacio")
    private String nombres;
    @NotEmpty(message = "El campo direccion no puede ser vacio")
    private String direccion;
    @NotEmpty(message = "El campo telefono no puede ser vacio")
    private String telefono;
    @Email(message = "No es una cuenta valida")
    private String email;
    @NotNull(message = "Debe seleccionar una carrera tecnica")
    private String carreraId;
    @NotNull(message = "Debe seleccionar una jornada")
    private String jornadaId;
    @NotNull(message = "Debe seleccionar un examen de admision")
    private String examenId;
}
