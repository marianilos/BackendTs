package edu.kalum.kalummanagement.core.controllers;
import com.google.gson.Gson;
import edu.kalum.kalummanagement.core.dao.services.IAspiranteService;
import edu.kalum.kalummanagement.core.dao.services.ICarreraTecnicaService;
import edu.kalum.kalummanagement.core.dao.services.IExamenAdmisionService;
import edu.kalum.kalummanagement.core.dao.services.IJornadaService;
import edu.kalum.kalummanagement.core.helpers.Utils;
import edu.kalum.kalummanagement.core.model.dtos.AspiranteCreateDTO;
import edu.kalum.kalummanagement.core.model.dtos.AspiranteResponseDTO;
import edu.kalum.kalummanagement.core.model.entities.Aspirante;
import edu.kalum.kalummanagement.core.model.entities.CarreraTecnica;
import edu.kalum.kalummanagement.core.model.entities.ExamenAdmision;
import edu.kalum.kalummanagement.core.model.entities.Jornada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/kalum-management/v1/aspirantes")
public class AspiranteController {
    private final RabbitTemplate queueSender;

    private Logger logger = LoggerFactory.getLogger(AspiranteController.class);
    @Autowired
    private IAspiranteService aspiranteService;
    @Autowired
    private IJornadaService jornadaService;
    @Autowired
    private IExamenAdmisionService examenAdmisionService;
    @Autowired
    private ICarreraTecnicaService carreraTecnicaService;
    @Autowired
    private Utils utils;

    public AspiranteController(RabbitTemplate queueSender){
        this.queueSender = queueSender;
    }

    @GetMapping("/test")
    public String send(){
        String message = "Testing rabbit";
        this.queueSender.convertAndSend("exchange-candidateprocess","expediente",message);
        return "ok, done!!";
    }

    @GetMapping
    public ResponseEntity<?> listarAspirantes(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando proceso de consulta de aspirantes");

        try{
            List<Aspirante> aspirantes = aspiranteService.findAll();
            if(aspirantes == null || aspirantes.size() == 0){
                logger.warn("No existen registros en la tabla aspirantes");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.info("Se proceso la consulta de forma exitosa");
                return new ResponseEntity<List<Aspirante>>(aspirantes, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = utils.getDataAccessException(response, e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /// HACER BUSQUEDA BY PAGE
    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarAspirantesByPage(@PathVariable Integer page){
        Map<String,Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page,5);
            Page<Aspirante> aspirantePage = aspiranteService.findAll(pageable);
            if(aspirantePage == null || aspirantePage.getSize() == 0) {
                logger.warn("No existen registros en la tabla de aspirantes");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else {
                logger.info("Se proceso la consulta exitosamente");
                return new ResponseEntity<Page<Aspirante>>(aspirantePage, HttpStatus.OK);
            }
        }catch(CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    ///METODO PARA HACER LA BUSQUEDA POR NO EXPEDIENTE
    @GetMapping("/{noExpediente}")
    public ResponseEntity<?> showAspirante(@PathVariable String noExpediente){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la busqueda de aspirante con numero de expediente ".concat(noExpediente));
        try {
            Aspirante aspirante = this.aspiranteService.findById(noExpediente);
            if(aspirante == null){
                logger.warn("No existe el aspirante con numero de expediente ".concat(noExpediente));
                response.put("mensaje","No existe el aspirante con numero de expediente ".concat(noExpediente));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("se proceso la busqueda de forma correcta");
                return new ResponseEntity<Aspirante>(aspirante, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    ///METODO PARA MODIFICAR ASPIRANTE
    /// Pregunta que debo poder modificar del aspirante? Nombres, Apellidos, Jornada, email y telefono unicamente?
    @PutMapping("/{noExpediente}")
    public ResponseEntity<?> update(@Valid @RequestBody AspiranteCreateDTO value, BindingResult result,@PathVariable String noExpediente){
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores",errores);
            logger.info("Se encuentran errores de validacion en la peticion");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            Aspirante aspirante = this.aspiranteService.findById(noExpediente);
            if(aspirante == null){
                response.put("mensaje","El aspirante con numero de expediente ".concat(noExpediente).concat(" no existe"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else {
                aspirante.setNombres(value.getNombres());
                this.aspiranteService.save(aspirante);
                response.put("mensaje","El aspirante fue modificado con exito");
                response.put("aspirante",aspirante);
                logger.info("El aspirante fue actualizado con exito");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    ///METODO PARA ELIMINAR ASPIRANTE
    @DeleteMapping("/{noExpediente}")
    public ResponseEntity<?> delete(@PathVariable String noExpediente){
        Map<String,Object> response = new HashMap<>();
        try{
            Aspirante aspirante = this.aspiranteService.findById(noExpediente);
            if(aspirante == null){
                response.put("mensaje","El aspirante con numero de expediente ".concat(noExpediente).concat(" no existe"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                this.aspiranteService.delete(aspirante);
                response.put("mensaje","El aspirante con numero de expediente ".concat(noExpediente).concat(" fue eliminado con exito"));
                response.put("aspirante",aspirante);
                logger.info("El aspirante fue eliminado con exito");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }
        }catch(CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    ///METODO PARA REALIZAR BUSQUEDA POR TERMINO
    @GetMapping("/search")
    public ResponseEntity<?> listarAspirantesPorTermino(@RequestParam("termino") String termino){
        Map<String,Object> response = new HashMap<>();
        try{
            List<Aspirante> aspirantes = this.aspiranteService.findAspiranteByTerm(termino);
            if(aspirantes == null || aspirantes.size() == 0){
                logger.warn("mensaje","No existe ninguna coincidencia para el aspirante");
                response.put("mensaje","No existe ninguna coincidencia para el aspirante");
                return new ResponseEntity<List<Aspirante>>(aspirantes, HttpStatus.NOT_FOUND);
            }else{
                response.put("mensaje","Se ejecuto la consulta existosamente");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
            }
        }catch(CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = utils.getDataAccessException(response,e);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    ///METODO PARA ENCOLAR EL PROCESO
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AspiranteCreateDTO value, BindingResult result){
        Map<String,Object> response = new HashMap<>();
        if (result.hasErrors()){
            List<String> errorers = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores",errorers);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try{
            ExamenAdmision examenAdmision = examenAdmisionService.findById(value.getExamenId());
            if(examenAdmision == null){
                response.put("mensaje","El examen de admision con el id ".concat(value.getExamenId().concat(" no existe.")));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            CarreraTecnica carreraTecnica = carreraTecnicaService.findById(value.getCarreraId());
            if(carreraTecnica == null){
                response.put("mensaje","La carrera tecnica con el id ".concat(value.getCarreraId()).concat(" no existe."));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            Jornada jornada = jornadaService.findById(value.getJornadaId());
            if(jornada == null){
                response.put("mensaje","La jornada con el id ".concat(value.getJornadaId()).concat(" no existe."));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            this.queueSender.convertAndSend("exchange-candidateprocess","expediente", new Gson().toJson(value));
            AspiranteResponseDTO aspiranteResponseDTO = new AspiranteResponseDTO();
            aspiranteResponseDTO.setHttpStatus(201);
            aspiranteResponseDTO.setMessage("Su solicitud fue procesada con exito, recibira una respuesta al correo ".concat(value.getEmail()));
            return new ResponseEntity<AspiranteResponseDTO>(aspiranteResponseDTO,HttpStatus.CREATED);

        }catch (CannotCreateTransactionException e){
            response = utils.getTransactionException(response,e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = utils.getDataAccessException(response, e);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
}
