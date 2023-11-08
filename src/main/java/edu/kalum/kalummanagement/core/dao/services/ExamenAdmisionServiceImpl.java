package edu.kalum.kalummanagement.core.dao.services;

import edu.kalum.kalummanagement.core.dao.IExamenAdmisionDao;
import edu.kalum.kalummanagement.core.model.entities.ExamenAdmision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class ExamenAdmisionServiceImpl implements IExamenAdmisionService{
    @Autowired
    private IExamenAdmisionDao examenAdmisionDao;
    @Override
    public List<ExamenAdmision> findAll() {
        return examenAdmisionDao.findAll();
    }

    @Override
    public List<ExamenAdmision> findExamenAdmisionByYear(int year) {
        return null;
    }

    @Override
    public Page<ExamenAdmision> findAll(Pageable pageable) {
        return examenAdmisionDao.findAll(pageable);
    }

    @Override
    public ExamenAdmision findById(String examenId) {
        return this.examenAdmisionDao.findById(examenId).orElse(null);
    }
    @Override
    public ExamenAdmision save(ExamenAdmision examenAdmision) {
        return examenAdmisionDao.save(examenAdmision);
    }

    @Override
    public void delete(ExamenAdmision examenAdmision) {
        examenAdmisionDao.delete(examenAdmision);

    }
}
