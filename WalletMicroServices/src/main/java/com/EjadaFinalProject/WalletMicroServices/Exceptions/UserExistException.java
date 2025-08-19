package com.EjadaFinalProject.WalletMicroServices.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
// This exception is thrown when a user already exists in the system.
public class UserExistException extends  RuntimeException
{
    public UserExistException(String message) {
        super(message);
    }
}
