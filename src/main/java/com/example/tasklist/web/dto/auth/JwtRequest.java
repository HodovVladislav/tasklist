package com.example.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(description = "email", example = "johndoe@gmail.com")
    @NotNull(message = "Username should not be empty.")
    private String username;

    @Schema(description = "password", example = "12345")
    @NotNull(message = "Password should not be empty.")
    private String password;

}
