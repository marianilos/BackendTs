package edu.kalum.kalummanagement.core.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="alumno")
@Entity

public class Alumno implements Serializable {
    @Id
    @Column(name="carne")
    private String carne;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="nombres")
    private String nombres;
    @Column(name="telefono")
    private String telefono;
    @Column(name="email")
    private String email;
    @Column(name="direccion")
    private String direccion;
    /*@OneToMany(mappedBy = "alumno",fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Inscripcion> inscripciones;*/
}
