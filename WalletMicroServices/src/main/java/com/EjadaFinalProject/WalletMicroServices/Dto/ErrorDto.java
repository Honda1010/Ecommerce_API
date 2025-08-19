package com.EjadaFinalProject.WalletMicroServices.Dto;

import java.time.LocalDateTime;

public class ErrorDto {
    private LocalDateTime timestamp;
    private String message;
    private String Details;

    public ErrorDto(LocalDateTime timestamp, String message,String details) {
        Details = details;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
