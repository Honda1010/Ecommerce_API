package com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductAlreadyExistInShopException extends RuntimeException {
    public ProductAlreadyExistInShopException(String message) {
        super(message);
    }
}
