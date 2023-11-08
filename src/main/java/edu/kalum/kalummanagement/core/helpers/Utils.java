package edu.kalum.kalummanagement.core.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.Map;

@Component
public class Utils {
    Logger logger = LoggerFactory.getLogger(Utils.class);
    public Map<String,Object> getTransactionException(Map<String,Object> response, CannotCreateTransactionException e){
        logger.error("Error al momento de conectarse a la base de datos");
        response.put("mensaje","Error al momento de conectarse a la base de datos");
        response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return response;
    }
    public Map<String,Object> getDataAccessException(Map<String,Object> response, DataAccessException e){
        logger.error("Error al momento de ejecutar la consulta a la base de datos");
        response.put("mensaje","Error al momento de ejecutar la consulta a la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return response;
    }
}
