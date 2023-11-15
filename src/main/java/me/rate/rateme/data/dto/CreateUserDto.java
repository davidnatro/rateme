package me.rate.rateme.data.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String username, @NotBlank String password) { }
