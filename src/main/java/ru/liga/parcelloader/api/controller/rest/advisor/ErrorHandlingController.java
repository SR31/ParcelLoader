package ru.liga.parcelloader.api.controller.rest.advisor;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.parcelloader.api.controller.rest.data.ErrorMessage;
import ru.liga.parcelloader.type.exception.NotSupportedParcelSymbol;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandlingController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleErrors(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                ErrorMessage
                        .builder()
                        .message(exception
                                .getBindingResult()
                                .getAllErrors()
                                .get(0)
                                .getDefaultMessage()
                        ).timestamp(LocalDateTime.now())
                        .build(),
                exception.getStatusCode()
                );
    }

    @ExceptionHandler(NotSupportedParcelSymbol.class)
    public ResponseEntity<ErrorMessage> handleErrors(NotSupportedParcelSymbol exception) {
        return new ResponseEntity<>(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage()
                        ).timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> handleErrors(ValidationException exception) {
        return new ResponseEntity<>(
                ErrorMessage
                        .builder()
                        .message("Передан невалидный объект:" + exception.getCause().getMessage()
                        ).timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
