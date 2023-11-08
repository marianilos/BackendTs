package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="inscripcion")
@Entity

public class Inscripcion implements Serializable {
    @Id
    @Column(name="inscripcion_id")
    private String inscripcionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carne", referencedColumnName = "carne")
    private Alumno carne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carrera_id",referencedColumnName = "carrera_id")
    private CarreraTecnica carreraId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jornada_id",referencedColumnName = "jornada_id")
    private Jornada jornadaId;

    @Column(name = "ciclo")
    private String ciclo;

    @Column(name = "fecha_inscripcion")
    private String fechaInscripcion;
}
