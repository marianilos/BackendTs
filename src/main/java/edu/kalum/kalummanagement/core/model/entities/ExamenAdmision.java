package edu.kalum.kalummanagement.core.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "examen_admision")
@Entity
public class ExamenAdmision implements Serializable {

    @Id
    @Column(name = "examen_id")
    private String examenId;

    @Column(name = "fecha_examen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExamen;
    @OneToMany(mappedBy = "examenAdmision", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Aspirante> aspirantes;
}
