package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resultado_examen_admision")
@Entity
@ToString

public class ResultadoExamenAdmision implements Serializable {
    @EmbeddedId
    private ResultadoExamenAdmisionId resultadoExamenAdmisionId;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nota")
    private Integer nota;
}
