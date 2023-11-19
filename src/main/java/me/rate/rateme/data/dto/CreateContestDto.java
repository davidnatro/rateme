package me.rate.rateme.data.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateContestDto(@NotBlank String name) { }
