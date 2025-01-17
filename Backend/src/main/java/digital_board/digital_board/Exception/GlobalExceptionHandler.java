package digital_board.digital_board.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import digital_board.digital_board.Dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler( ResourceNotFoundException ex) {
        String message=ex.getMessage();
        ApiResponse apiresponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
    }


}
