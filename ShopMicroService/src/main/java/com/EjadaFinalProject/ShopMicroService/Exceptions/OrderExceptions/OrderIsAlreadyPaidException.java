package com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrderIsAlreadyPaidException extends RuntimeException {
    public OrderIsAlreadyPaidException(String message) {
        super(message);
    }
}
