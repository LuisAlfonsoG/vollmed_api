package med.voll.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity ErrorHandler404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity ErrorHandler400(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(ErrorData::new);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ValidationError.class)
    public ResponseEntity ErrorHandlerValidatorError(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity ErrorHandlerValidatorException(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private record ErrorData(String field, String error_msg){
        public ErrorData(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
