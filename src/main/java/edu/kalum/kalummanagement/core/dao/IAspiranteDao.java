package edu.kalum.kalummanagement.core.dao;

import edu.kalum.kalummanagement.core.model.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAspiranteDao extends JpaRepository<Aspirante,String> {
    @Query("select a from Aspirante a where a.apellidos like %?1%")
    public List<Aspirante> findAspiranteByTerm(String termino);
}
