package edu.kalum.kalummanagement.core.dao;

import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICarreraTecnicaDao extends JpaRepository<CarreraTecnica,String> {
    @Query("select c from CarreraTecnica c where c.carreraTecnica like %?1%")
    public List<CarreraTecnica> findCarreraTecnicaByTerm(String termino);
}
