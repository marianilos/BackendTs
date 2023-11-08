package edu.kalum.kalummanagement.core.dao;

import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import edu.kalum.kalummanagement.core.model.entities.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IJornadaDao extends JpaRepository<Jornada,String> {
    @Query("select j from Jornada j where j.jornada like %?1%")
    public List<Jornada> findJornadaByTerm(String termino);
}
