package com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InventoryProductNotFoundException extends RuntimeException{
    public InventoryProductNotFoundException(String message) {
        super(message);
    }
}
