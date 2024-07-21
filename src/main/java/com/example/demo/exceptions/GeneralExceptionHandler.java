package com.example.demo.exceptions;


import com.waystech.jwtss.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<GenericResponse> handleGlobalExceptions(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        GenericResponse response =new GenericResponse();
        response.setStatus("Success");
        response.setMessage(Arrays.toString(errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<GenericResponse> handleExistException(UserExistException exception) {
        String errorMessage = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse(errorMessage));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<GenericResponse> handleNOtFoundException(OrderNotFoundException exception){
        String errorMessage = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>(errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        GenericResponse response = new GenericResponse("Please verify your account");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(CommonApplicationException.class)
    public ResponseEntity<GenericResponse> handleGeneralException(CommonApplicationException ex) {
        GenericResponse response = new GenericResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
