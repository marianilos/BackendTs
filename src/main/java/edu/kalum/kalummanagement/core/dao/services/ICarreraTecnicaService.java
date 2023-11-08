package edu.kalum.kalummanagement.core.dao.services;

import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface ICarreraTecnicaService {
    public List<CarreraTecnica> findAll();
    public List<CarreraTecnica> findCarreraTecnicaByTerm(String termino);
    public Page<CarreraTecnica> findAll(Pageable pageable);
    public CarreraTecnica findById(String carreraId);
    public CarreraTecnica save(CarreraTecnica carreraTecnica);
    public void delete(CarreraTecnica carreraTecnica);
}
