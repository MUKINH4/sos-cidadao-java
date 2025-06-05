package sos_cidadao.api.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ex.getMessage(), 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(ex.getMessage(), 500));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String constraintName = null;
        Throwable cause = ex.getCause();
        if (cause instanceof ConstraintViolationException) {
            constraintName = ((ConstraintViolationException) cause).getConstraintName();
        }
        String message;
        if ("usuarios_email_key".equals(constraintName)) {
            message = "Email já em uso";
        } else if (constraintName != null) {
            message = "Violação de integridade de dados: " + constraintName;
        } else {
            message = "Violação de integridade de dados.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponse.of(message, HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Erro ao desserializar JSON: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(message, 400));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException ex) {
        String message = "O token de autenticação expirou. Por favor, faça login novamente.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.of(message, 401));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        System.out.println(ex.getCause());
        if (ex.getCause() instanceof ConstraintViolationException) {
            String message = "Erro de violação de restrição: " + ex.getCause().getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(message, 409));
        };
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of("Erro interno do servidor: " + ex.getMessage(), 500));
    }
}
