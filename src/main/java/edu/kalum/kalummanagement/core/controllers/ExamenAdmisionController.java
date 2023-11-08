package edu.kalum.kalummanagement.core.controllers;
import edu.kalum.kalummanagement.core.dao.IExamenAdmisionDao;
import edu.kalum.kalummanagement.core.dao.services.IExamenAdmisionService;
import edu.kalum.kalummanagement.core.helpers.Utils;
import edu.kalum.kalummanagement.core.model.entities.ExamenAdmision;
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
@RequestMapping("/kalum-management/v1/examenes-admision")
public class ExamenAdmisionController {
    private Logger logger = LoggerFactory.getLogger(ExamenAdmisionController.class);
    @Autowired
    private IExamenAdmisionService examenAdmisionService;
    @Autowired
    private Utils utils;

    @GetMapping
    public ResponseEntity<?> listarExamenesAdmision(){
        Map<String,Object> response = new HashMap<>();
        this.logger.debug("Iniciando el proceso de consulta de Examenes de Admision");
        try{
            List<ExamenAdmision> examenAdmisionList = examenAdmisionService.findAll();
            if (examenAdmisionList == null || examenAdmisionList.size() == 0){
                logger.warn("No existen registros en la tabla de examenes de admision");
                return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
            }else {
                logger.info("Se proceso la consulta de forma exitosa");
                return new ResponseEntity<List<ExamenAdmision>>(examenAdmisionList,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarExamenesAdmisionByPage(@PathVariable Integer page){
        Map<String,Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page,5);
            Page<ExamenAdmision> examenAdmisionPage = examenAdmisionService.findAll(pageable);
            if (examenAdmisionPage == null || examenAdmisionPage.getSize() == 0){
                logger.warn("No existen registros en la tabla de Examenes de Admision");
                return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
            }else{
                logger.info("Se proceso la consulta exitosamente");
                return new ResponseEntity<Page<ExamenAdmision>>(examenAdmisionPage,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/{examenId}")
    public ResponseEntity<?> showExamenAdmision(@PathVariable String examenId){
        Map<String,Object> response = new HashMap<>();
        logger.info("Iniciando el proceso de busqueda del examen de admision con Id ".concat(examenId));
        try{
            ExamenAdmision examenAdmision = examenAdmisionService.findById(examenId);
            if (examenAdmision == null){
                logger.warn("No existen registros para el examen de admision con Id ".concat(examenId));
                response.put("mensaje","No existen registros para el examen de admision con Id ".concat(examenId));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else {
                logger.info("Se proceso la solicitud exitosamente");
                return new ResponseEntity<ExamenAdmision>(examenAdmision,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/{examenId}")
    public ResponseEntity<?> delete(@PathVariable String examenId){
        Map<String,Object> response = new HashMap<>();
        try{
            ExamenAdmision examenAdmision = this.examenAdmisionService.findById(examenId);
            if (examenAdmision == null){
                logger.warn("No existen registros para el examen de admision con Id ".concat(examenId));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else{
                this.examenAdmisionService.delete(examenAdmision);
                response.put("mensaje","El examen de admision con Id ".concat(examenId).concat(" ha sido eliminado exitosamente"));
                response.put("examenAdmision",examenAdmision);
                logger.info("El examen de admision fue eliminado exitosamente");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> listarExamenAdmisionByYear(@RequestParam("anio")Integer anio){
        Map<String,Object> response = new HashMap<>();
        try{
            List<ExamenAdmision> examenesAdmision = this.examenAdmisionService.findExamenAdmisionByYear(anio);
            if(examenesAdmision == null || examenesAdmision.size() == 0){
                logger.warn("No existe ninguna coincidencia para algun examen de admision");
                response.put("mensaje","No existe ninguna coincidencia para algun examen de admision");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }else{
                logger.info("Se ejecuto la consulta exitosamente");
                return new ResponseEntity<List<ExamenAdmision>>(examenesAdmision,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

/// NECESITO HACER UN SERVICIO PARA AGREGAR Y UNO PARA MODIFICAR EXAMENES DE ADMISION?