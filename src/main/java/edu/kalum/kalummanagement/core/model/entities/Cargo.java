package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cargo")
@Entity

public class Cargo implements Serializable {
    @Id
    @Column(name="cargo_id")
    private String cargoId;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="prefijo")
    private String prefijo;
    @Column(name="monto")
    private Double monto;
    @Column(name="genera_mora")
    private Integer generaMora;
    @Column(name="porcentaje_mora")
    private Integer procentajeMora;
}
