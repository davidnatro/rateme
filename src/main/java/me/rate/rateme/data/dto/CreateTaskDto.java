package me.rate.rateme.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import me.rate.rateme.data.enums.Difficulty;

public record CreateTaskDto(@NotBlank String name, @NotBlank String description,
                            @NotBlank String inputFormat, @NotBlank String outputFormat,
                            @NotBlank String codeExample, @NotNull Difficulty difficulty,
                            @NotNull Map<String, Object> inputData,
                            @NotNull Map<String, Object> outputData) { }