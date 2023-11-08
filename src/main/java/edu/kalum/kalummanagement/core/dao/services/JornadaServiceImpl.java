package edu.kalum.kalummanagement.core.dao.services;

import edu.kalum.kalummanagement.core.dao.IJornadaDao;
import edu.kalum.kalummanagement.core.model.entities.Jornada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Service
public class JornadaServiceImpl implements IJornadaService{

    @Autowired
    private IJornadaDao jornadaDao;
    @Override
    public List<Jornada> findAll() {
        return jornadaDao.findAll();
    }

    @Override
    public List<Jornada> findJornadaByTerm(String termino) {
        return jornadaDao.findJornadaByTerm(termino);
    }

    @Override
    public Page<Jornada> findAll(Pageable pageable) {
        return jornadaDao.findAll(pageable);
    }

    @Override
    public Jornada findById(String jornadaId) {
        return this.jornadaDao.findById(jornadaId).orElse(null);
    }

    @Override
    public Jornada save(Jornada jornada) {
        return jornadaDao.save(jornada);
    }

    @Override
    public void delete(Jornada jornada) {
        jornadaDao.delete(jornada);

    }
}
