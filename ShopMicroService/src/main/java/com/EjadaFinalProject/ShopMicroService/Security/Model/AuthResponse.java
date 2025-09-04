package com.EjadaFinalProject.ShopMicroService.Security.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AuthResponse {
    private String Token;

    public AuthResponse(String token) {
        Token = token;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
