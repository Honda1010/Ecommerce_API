package com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class InventoryServiceIsNotAvailableException extends RuntimeException {
    public InventoryServiceIsNotAvailableException(String message){
        super(message);
    }
}
