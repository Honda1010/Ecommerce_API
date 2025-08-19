package com.EjadaFinalProject.WalletMicroServices.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BalanceNotEnoughException extends RuntimeException{
    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
