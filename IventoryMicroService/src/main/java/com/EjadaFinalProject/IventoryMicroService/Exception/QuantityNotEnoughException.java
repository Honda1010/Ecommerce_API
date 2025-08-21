package com.EjadaFinalProject.IventoryMicroService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuantityNotEnoughException extends RuntimeException {
    public QuantityNotEnoughException(String message) {
        super(message);
    }
}
