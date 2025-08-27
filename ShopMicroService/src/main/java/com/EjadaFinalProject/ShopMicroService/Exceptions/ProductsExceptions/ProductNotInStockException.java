package com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductNotInStockException extends RuntimeException{
    public ProductNotInStockException(String message) {
        super(message);
    }
}
