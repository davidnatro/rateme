package me.rate.rateme.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmissionDto(@JsonProperty("source_code") String sourceCode,
                            @JsonProperty("language_id") Integer languageId,
                            @JsonProperty("expected_output") String expectedOutput,
                            String stdin) { }
