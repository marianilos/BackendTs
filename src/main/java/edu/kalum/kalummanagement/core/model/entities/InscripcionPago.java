package edu.kalum.kalummanagement.core.model.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="inscripcion_pago")
//@Entity
@ToString

public class InscripcionPago implements Serializable {
    @EmbeddedId
    private InscripcionPagoId inscripcionPagoId;

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    @Column(name = "monto")
    private Long monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_expediente", referencedColumnName = "no_expediente")
    private Aspirante aspirante;
}
