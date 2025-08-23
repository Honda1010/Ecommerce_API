package com.EjadaFinalProject.WalletMicroServices.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WalletAlreadyExistException extends RuntimeException {
    public WalletAlreadyExistException(String message) {
        super(message);
    }
}
