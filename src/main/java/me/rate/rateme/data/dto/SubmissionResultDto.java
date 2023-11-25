package me.rate.rateme.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmissionResultDto(String stdout,
                                  @JsonProperty("compile_output") String compileOutput,
                                  @JsonProperty("status") SubmissionStatusDto statusDto) {

  public record SubmissionStatusDto(String description) { }
}
