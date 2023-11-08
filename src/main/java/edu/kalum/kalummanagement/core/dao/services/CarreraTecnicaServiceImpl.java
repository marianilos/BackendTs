package edu.kalum.kalummanagement.core.dao.services;

import edu.kalum.kalummanagement.core.dao.ICarreraTecnicaDao;
import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraTecnicaServiceImpl implements ICarreraTecnicaService {
    @Autowired
    private ICarreraTecnicaDao carreraTecnicaDao;
    @Override
    public List<CarreraTecnica> findAll() {
        return this.carreraTecnicaDao.findAll();
    }

    @Override
    public List<CarreraTecnica> findCarreraTecnicaByTerm(String termino) {
        return this.carreraTecnicaDao.findCarreraTecnicaByTerm(termino);
    }

    @Override
    public Page<CarreraTecnica> findAll(Pageable pageable) {
        return this.carreraTecnicaDao.findAll(pageable);
    }

    @Override
    public CarreraTecnica findById(String carreraId) {
        return this.carreraTecnicaDao.findById(carreraId).orElse(null);
    }

    @Override
    public CarreraTecnica save(CarreraTecnica carreraTecnica) {
        return this.carreraTecnicaDao.save(carreraTecnica);
    }

    @Override
    public void delete(CarreraTecnica carreraTecnica) {
        this.carreraTecnicaDao.delete(carreraTecnica);
    }
}
