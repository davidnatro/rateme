package me.rate.rateme.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmissionResultDto(String stdout, double time, double memory,
                                  @JsonProperty("compile_output") String compileOutput,
                                  SubmissionStatusDto status) {

  public record SubmissionStatusDto(String description) { }
}
