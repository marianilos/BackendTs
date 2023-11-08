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
@Table(name = "carrera_tecnica")
@Entity
public class CarreraTecnica implements Serializable {
    @Id
    @Column(name = "carrera_id")
    private String carreraId;
    @Column(name= "carrera_tecnica")
    private String carreraTecnica;
    @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Aspirante> aspirantes;

}
