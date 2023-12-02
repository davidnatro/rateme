package me.rate.rateme.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmissionDto(@NotNull Long taskId,
                            @Valid @NotNull JudgeSubmissionDto judgeSubmission) {

  public record JudgeSubmissionDto(@NotBlank @JsonProperty("source_code") String sourceCode,
                                   @NotNull @JsonProperty("language_id") Long languageId,
                                   String stdin) { }
}
