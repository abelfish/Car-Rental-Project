package ea.project.rentalapp.service.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionsInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ExceptionsInterceptor.class);

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<?> handleInvalidInputException(Exception e) {
        logger.info(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<?> handleException(Exception e) {
        logger.info(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleException(RuntimeException e) {
        logger.debug(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
