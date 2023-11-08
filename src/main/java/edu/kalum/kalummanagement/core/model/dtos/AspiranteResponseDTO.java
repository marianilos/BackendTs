package edu.kalum.kalummanagement.core.model.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AspiranteResponseDTO implements Serializable {
    private int httpStatus;
    private String message;
}
