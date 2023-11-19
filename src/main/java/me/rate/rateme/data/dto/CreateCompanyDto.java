package me.rate.rateme.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCompanyDto(@NotBlank String name, @NotBlank @Email String email) { }
