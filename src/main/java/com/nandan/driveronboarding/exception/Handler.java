package com.nandan.driveronboarding.exception;

import com.nandan.driveronboarding.responses.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BindingErrorsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleBindingException(BindingErrorsException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
            errorMessages.add(fieldError.getDefaultMessage());
        });
        return new ErrorMessage("Validation failed", errorMessages);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An internal server error occurred. Please try again later.");
    }

    @ExceptionHandler(VehicleRegistrationException.class)
    @ResponseBody
    public ResponseEntity<String> handleVehicleRegistrationException(VehicleRegistrationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An internal server error occurred. Please try again later.");
    }
}
