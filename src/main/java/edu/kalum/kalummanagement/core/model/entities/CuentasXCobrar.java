package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="cuentas_x_cobrar")
//@Entity

public class CuentasXCobrar implements Serializable {
    @EmbeddedId
    private CuentasXCobrarId cuentasXCobrarId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cargo_id", referencedColumnName = "cargo_id")
    private Cargo cargoId;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="fecha_cargo")
    private String fechaCargo; // Tengo mis dudas porque en la base de datos se declara como datetime

    @Column(name="monto")
    private Double monto;

    @Column(name="mora")
    private Double mora;

    @Column(name="descuento")
    private Double descuento;
}
