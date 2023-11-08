package edu.kalum.kalummanagement.core.dao;

import edu.kalum.kalummanagement.core.model.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAlumnoDao extends JpaRepository<Alumno,String> {
  //  @Query("select a from Alumno a where a.apellidos like %%")
    //public List<Alumno> findAlumnoByTerm(String termino);
}
