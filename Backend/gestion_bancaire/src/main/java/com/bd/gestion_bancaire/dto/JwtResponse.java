package com.bd.gestion_bancaire.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private String email;
    private List<String> roles;

    public JwtResponse(String token, String email, List<String> roles){
        this.token = token;
        this.email = email;
        this.roles = roles;
    }

}
