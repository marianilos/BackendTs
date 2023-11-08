package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="aspirante")
@Entity

public class Aspirante implements Serializable {
    @Id
    @Column(name="no_expediente")
    private String noExpediente;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="nombres")
    private String nombres;
    @Column(name="direccion")
    private String direccion;
    @Column(name="telefono")
    private String telefono;
    @Column(name="email")
    private String email;
    @Column(name="estatus")
    private String estatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carrera_id", referencedColumnName = "carrera_id")
    private CarreraTecnica carreraTecnica;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examen_id", referencedColumnName = "examen_id")
    private ExamenAdmision examenAdmision;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jornada_id", referencedColumnName = "jornada_id")
    private Jornada jornada;

}
