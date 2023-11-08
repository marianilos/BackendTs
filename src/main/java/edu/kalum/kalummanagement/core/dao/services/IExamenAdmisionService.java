package edu.kalum.kalummanagement.core.dao.services;
import edu.kalum.kalummanagement.core.model.entities.ExamenAdmision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IExamenAdmisionService {
    public List<ExamenAdmision> findAll();
    public List<ExamenAdmision> findExamenAdmisionByYear(int year);
    public Page<ExamenAdmision> findAll(Pageable pageable);
    public ExamenAdmision findById(String examenId);
    public ExamenAdmision save(ExamenAdmision examenAdmision);
    public void delete(ExamenAdmision examenAdmision);
}
