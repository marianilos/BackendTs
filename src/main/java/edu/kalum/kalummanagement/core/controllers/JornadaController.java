package edu.kalum.kalummanagement.core.controllers;
import edu.kalum.kalummanagement.core.dao.services.IJornadaService;
import edu.kalum.kalummanagement.core.helpers.Utils;
import edu.kalum.kalummanagement.core.model.entities.Jornada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kalum-management/v1/jornadas")
public class JornadaController {
    private Logger logger = LoggerFactory.getLogger(JornadaController.class);
    @Autowired
    private IJornadaService jornadaService;
    @Autowired
    private Utils utils;

    @GetMapping
    public ResponseEntity<?> listarJornadas(){
        Map<String,Object> response = new HashMap<>();
        this.logger.debug("Iniciando el proceso de consulta de Jornadas");
        try{
            List<Jornada> jornadas = jornadaService.findAll();
            if(jornadas == null || jornadas.size() == 0){
                logger.warn("No existen registros en la tabla jornadas");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else{
                logger.info("Se proceso la consulta de forma exitosa");
                return new ResponseEntity<List<Jornada>>(jornadas,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarJornadasByPage(@PathVariable Integer page){
        Map<String,Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page,5);
            Page<Jornada> jornadaPage = jornadaService.findAll(pageable);
            if(jornadaPage == null || jornadaPage.getSize() == 0){
                logger.warn("No existen registros en la tabla de jornada");
                return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
            }else{
                logger.info("Se proceso la consulta exitosamente");
                return new ResponseEntity<Page<Jornada>>(jornadaPage,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/{jornadaId}")
    public ResponseEntity<?> showJornada(@PathVariable String jornadaId){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de busqueda de jornada por medio del Id ".concat(jornadaId));
        try{
            Jornada jornada = this.jornadaService.findById(jornadaId);
            if (jornada == null){
                logger.warn("No existe la jornada con el Id ".concat(jornadaId));
                response.put("mensaje","No existe la jornada con el Id ".concat(jornadaId));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Se proceso la consulta exitosamente");
                return new ResponseEntity<Jornada>(jornada,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/{jornadaId}")
    public ResponseEntity<?> delete(@PathVariable String jornadaId){
        Map<String,Object> response = new HashMap<>();
        try{
            Jornada jornada = this.jornadaService.findById(jornadaId);
            if (jornada == null){
                response.put("mensaje","La jornada con el id ".concat(jornadaId).concat(" no existe"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                this.jornadaService.delete(jornada);
                response.put("mensaje","La jornada con el id ".concat(jornadaId).concat(" fue eliminada con exito"));
                response.put("jornada",jornada);
                logger.info("La jornada fue eliminada con exito");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> listarJornadasPorTermino(@RequestParam("termino")String termino){
        Map<String,Object> response = new HashMap<>();
        try{
            List<Jornada> jornadas = this.jornadaService.findJornadaByTerm(termino);
            if (jornadas == null || jornadas.size() == 0){
                logger.warn("No existe ninguna coincidencia para alguna jornada");
                response.put("mensaje","No existe ninguna coincidencia para alguna jornada");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }else{
                logger.info("Se ejecuto la consulta exitosamente");
                return new ResponseEntity<List<Jornada>>(jornadas,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}

/// NECESITO HACER UN SERVICIO PARA AGREGAR Y UNO PARA MODIFICAR JORNADAS?
