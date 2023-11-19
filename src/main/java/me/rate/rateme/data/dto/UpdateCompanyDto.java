package me.rate.rateme.data.dto;

import jakarta.validation.constraints.Email;

public record UpdateCompanyDto(String name, @Email String email) { }
