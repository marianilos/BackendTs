package edu.kalum.kalummanagement.core.dao;
import edu.kalum.kalummanagement.core.model.entities.ExamenAdmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IExamenAdmisionDao extends JpaRepository<ExamenAdmision,String> {
    @Query("select ea from ExamenAdmision ea where YEAR(ea.fechaExamen) = ?1")
    public List<ExamenAdmision> findExamenAdmisionByYear(int anio);

}
