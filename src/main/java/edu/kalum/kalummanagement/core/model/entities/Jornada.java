package edu.kalum.kalummanagement.core.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jornada")
@Entity
public class Jornada implements Serializable {
    @Id
    @Column(name = "jornada_id")
    private String jornadaId;
    @Column(name = "jornada")
    private String jornada;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "jornada", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Aspirante> aspirantes;
}
