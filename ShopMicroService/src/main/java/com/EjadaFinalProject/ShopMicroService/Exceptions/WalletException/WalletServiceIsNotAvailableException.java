package com.EjadaFinalProject.ShopMicroService.Exceptions.WalletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class WalletServiceIsNotAvailableException extends RuntimeException {
    public WalletServiceIsNotAvailableException(String message) {
        super(message);
    }
}
