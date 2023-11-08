package edu.kalum.kalummanagement.core.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InscripcionPagoId implements Serializable {
    @Column(name = "anio")
    private String anio;

    @Column(name = "no_expediente")
    private String noExpediente;

    @Column(name = "boleta_pago")
    private String boletaPago;
}
