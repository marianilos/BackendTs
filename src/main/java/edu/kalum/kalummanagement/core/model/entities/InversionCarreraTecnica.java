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
@Table(name = "inversion_carrera_tecnica")
@Entity
@ToString

public class InversionCarreraTecnica implements Serializable {
    @Id
    @Column(name = "inversion_id")
    private String inversionId;

    @Column(name = "monto_inscripcion")
    private Double montoInscripcion;

    @Column(name = "numero_pagos")
    private Integer numeroPagos;

    @Column(name = "monto_pago")
    private Double montoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id", referencedColumnName = "carrera_id")
    private CarreraTecnica carreraTecnica;
}
