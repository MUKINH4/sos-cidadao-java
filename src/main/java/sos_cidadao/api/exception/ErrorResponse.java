package sos_cidadao.api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    int status,
    String message,
    LocalDateTime timestamp
){

    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(status, message, LocalDateTime.now());
    } 
}
