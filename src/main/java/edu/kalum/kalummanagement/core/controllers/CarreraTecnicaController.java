package edu.kalum.kalummanagement.core.controllers;

import edu.kalum.kalummanagement.core.dao.services.ICarreraTecnicaService;
import edu.kalum.kalummanagement.core.model.dtos.CarreraTecnicaCreateDTO;
import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-management/v1/carreras-tecnicas")
public class CarreraTecnicaController {
    @Autowired
    private ICarreraTecnicaService carreraTecnicaServices;

    private Logger logger = LoggerFactory.getLogger(CarreraTecnicaController.class);
    @GetMapping
    public ResponseEntity<?> listarCarrerasTecnicas(){
        Map<String,Object> response = new HashMap<>();
        this.logger.debug("Iniciando el proceso de consulta de carreras tecnicas");
        try{
            List<CarreraTecnica> carrerasTecnicas = this.carreraTecnicaServices.findAll();
            if(carrerasTecnicas == null && carrerasTecnicas.isEmpty()){
                logger.warn("No existen registros para la entidad carrera tecnica");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.info("se ejecuto la consulta exitosamente");
                return new ResponseEntity<List<CarreraTecnica>>(carrerasTecnicas,HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /// METODO PARA LISTAR CARRERAS POR PAGINA
    @GetMapping("/page/{page}")
    public ResponseEntity<?> listCarrerasTecnicasByPage(@PathVariable Integer page){
        Map<String,Object> response = new HashMap<>();
        try {
            Pageable pageable = PageRequest.of(page,5);
            Page<CarreraTecnica> carreraTecnicaPage = carreraTecnicaServices.findAll(pageable);
            if(carreraTecnicaPage == null || carreraTecnicaPage.getSize() == 0){
                logger.warn("No existen registros en la tabla de carreras tecnicas");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.info("Se proceso la consulta exitosamente");
                return new ResponseEntity<Page<CarreraTecnica>>(carreraTecnicaPage, HttpStatus.OK);
            }
        }catch(CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    /// ACCION PARA CREAR UNA CARRERA TECNICA
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CarreraTecnicaCreateDTO value, BindingResult result){
        Map<String,Object> response = new HashMap<>();
        if (result.hasErrors() == true){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores",errores);
            logger.info("Se encontraron errores al momento de validar la informacion");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try{
            CarreraTecnica carreraTecnica = new CarreraTecnica();
            carreraTecnica.setCarreraId(UUID.randomUUID().toString());
            carreraTecnica.setCarreraTecnica(value.getCarreraTecnica());
            this.carreraTecnicaServices.save(carreraTecnica);
            logger.info("Se creo la nueva carrera tecnica de forma exitosa");
            response.put("mensaje","la carrera tecnica fue creada de forma exitosa");
            response.put("carreraTecnica",carreraTecnica);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    ///METODO PARA HACER LA BUSQUEDA POR CARRERA
    @GetMapping("/{carreraId}")
    public ResponseEntity<?> showCarreraTecnica(@PathVariable String carreraId){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando proceso de busqueda de la carrera tecnica con id ".concat(carreraId));
        try{
            CarreraTecnica carreraTecnica = this.carreraTecnicaServices.findById(carreraId);
            if (carreraTecnica == null){
                logger.warn("No existe la carrera tecnica con Id ".concat(carreraId));
                response.put("mensaje","No existe la carrera tecnica con id ".concat(carreraId));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Se proceso la busqueda de forma correcta");
                return new ResponseEntity<CarreraTecnica>(carreraTecnica, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = this.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
///SERVICIO PARA MODIFICAR CARRERA TECNICA
    @PutMapping("/{carreraId}")
    ResponseEntity<?> update(@Valid @RequestBody CarreraTecnicaCreateDTO value, BindingResult result, @PathVariable String carreraId) {
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores", errores);
            logger.info("Se encontraron errores de validacion en la peticion");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            CarreraTecnica carreraTecnica = this.carreraTecnicaServices.findById(carreraId);
            if (carreraTecnica == null){
                response.put("mensaje", "la carrera con el id ".concat(carreraId).concat(" no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                carreraTecnica.setCarreraTecnica(value.getCarreraTecnica());
                this.carreraTecnicaServices.save(carreraTecnica);
                response.put("mensaje","la carrera tecnica fue actualizada con exito");
                response.put("carreraTecnica", carreraTecnica);
                logger.info("La carrera tecnica fue actualizada con exito");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = this.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    ///METODO PARA ELIMINAR
    @DeleteMapping("/{carreraId}")
    public ResponseEntity<?> delete(@PathVariable String carreraId) {
        Map<String, Object> response = new HashMap<>();
        try {
            CarreraTecnica carreraTecnica = this.carreraTecnicaServices.findById(carreraId);
            if (carreraTecnica == null) {
                response.put("mensaje", "La carrera Tecnica con el id ".concat(carreraId).concat(" no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            } else{
                this.carreraTecnicaServices.delete(carreraTecnica);
                response.put("mensaje", "La carrera tecnica con id ".concat(carreraId).concat(" fue eliminada con exito"));
                response.put("carreraTecnica", carreraTecnica);
                logger.info("la carrera tecnica fue eliminada con exito");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
            }
        } catch (CannotCreateTransactionException e) {
            response = this.getTransactionException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (DataAccessException e) {
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> listarCarrerasTecnicasPorTermino(@RequestParam("termino") String termino){
        Map<String,Object> response = new HashMap<>();
        try {
            List<CarreraTecnica> carreraTecnicas = this.carreraTecnicaServices.findCarreraTecnicaByTerm(termino);
            if (carreraTecnicas == null || carreraTecnicas.size() == 0) {
                logger.warn("No existe ninguna coincidencia para alguna carrera ternica");
                response.put("mensaje", "No existe ninguna coincidencia para alguna carrera ternica");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); /// Porque es OK y no es NOT FOUND?
            } else {
                logger.info("Se ejecuto la consulta exitosamente");
                return new ResponseEntity<List<CarreraTecnica>>(carreraTecnicas, HttpStatus.OK);
            }
        } catch (CannotCreateTransactionException e){
            response = this.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        } catch (DataAccessException e){
            response = this.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private Map<String,Object> getTransactionException(Map<String,Object> response,CannotCreateTransactionException e){
        logger.error("Error al momento de conectarse a la base de datos");
        response.put("mensaje","Error al momento de conectarse a la base de datos");
        response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return response;
    }
    private Map<String,Object> getDataAccessException(Map<String,Object> response, DataAccessException e){
        logger.error("Error al momento de ejecutar la consulta a la base de datos");
        response.put("mensaje","Error al momento de ejecutar la consulta a la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return response;
    }
}
