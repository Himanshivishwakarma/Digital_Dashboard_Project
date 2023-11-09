package digital_board.digital_board.Exception;

import java.util.stream.Stream;

import digital_board.digital_board.Entity.ExceptionResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String message;
    public ResourceNotFoundException(String message) {
        this.message = message;
    }
   

}
