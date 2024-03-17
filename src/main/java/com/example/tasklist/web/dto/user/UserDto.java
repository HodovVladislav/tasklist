package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    @NotNull(message = "Id should not be empty", groups = OnUpdate.class)
    private long id;

    @NotNull(message = "Name should not be empty", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name should be shorter than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Username should not be empty", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username should be shorter than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password should not be empty", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation should not be empty", groups = {OnCreate.class})
    private String passwordConfirmation;

}
