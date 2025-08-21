package com.EjadaFinalProject.IventoryMicroService.Exception;

import com.EjadaFinalProject.IventoryMicroService.Dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends  ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDto> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDto errorDetails = new ErrorDto(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ErrorDto>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDto errorDetails = new ErrorDto(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorDto>(errorDetails, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(QuantityNotEnoughException.class)
    public final ResponseEntity<ErrorDto> handleWalletNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDto errorDetails = new ErrorDto(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ErrorDto>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDto errorDetails = new ErrorDto(LocalDateTime.now(),"Total Error:"+ex.getErrorCount()+" Fisrt Error: "+ex.getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);

    }
}
