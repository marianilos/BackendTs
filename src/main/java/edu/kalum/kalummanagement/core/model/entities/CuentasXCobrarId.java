package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CuentasXCobrarId implements Serializable {
    @Column(name="cargo")
    private String cargo;

    @Column(name="anio")
    private String anio;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="carne",referencedColumnName = "carne");
//    private Alumno carne;
}
