package com.example.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

    @NotNull(message = "Username should not be empty.")
    private String username;

    @NotNull(message = "Password should not be empty.")
    private String password;

}
