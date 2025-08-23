package com.EjadaFinalProject.ShopMicroService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductQuntityIsNotEnoughInStockException extends RuntimeException {
    public ProductQuntityIsNotEnoughInStockException(String message) {
        super(message);
    }
}
