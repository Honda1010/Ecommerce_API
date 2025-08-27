package com.EjadaFinalProject.ShopMicroService.Exceptions.PymentException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotCompletedPaymentCancelException extends RuntimeException{
    public NotCompletedPaymentCancelException(String message) {
        super(message);
    }
}
