package milliy.anonymous.milliytravel.exception.handler;

import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.exception.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.NonUniqueResultException;
import java.sql.SQLException;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler({ItemAlreadyExistsException.class, ItemNotFoundException.class,
            AppBadRequestException.class, NullPointerException.class, IllegalArgumentException.class,
            FileUploadException.class, NonUniqueResultException.class, SQLException.class})
    public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
        log.warn("<< handleBadRequestException " + e);
        return createResponse(e.getMessage());
    }

    @ExceptionHandler({TokenNotValidException.class, UsernameNotFoundException.class,
            BadCredentialsException.class})
    public ResponseEntity<?> handleTokenException(RuntimeException e) {
        log.warn("<< handleForbiddenException " + e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

//    @ExceptionHandler({ClassCastException.class})
//    public ResponseEntity<?> handleNotFoundTokenException() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Found Token!");
//    }

    @ExceptionHandler({AppForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(RuntimeException e) {
        log.warn("<< handleForbiddenException " + e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({AppNotAcceptableException.class})
    public ResponseEntity<?> handleNotAcceptableException(RuntimeException e) {
        log.warn("<< handleNotAcceptableException " + e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }
}
