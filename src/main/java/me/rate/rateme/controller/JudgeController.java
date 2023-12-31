package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.data.dto.SubmissionDto;
import me.rate.rateme.data.dto.SubmissionResponseDto;
import me.rate.rateme.data.dto.SubmissionResultDto;
import me.rate.rateme.service.JudgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/judge")
@Tag(name = "Judge", description = "Judge API")
public class JudgeController {

  private final JudgeService judgeService;

  @GetMapping("/languages")
  @Operation(summary = "get all available programming languages")
  @ApiResponses(
      value = { @ApiResponse(responseCode = "200", description = "List of languages"),
          @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<List<LanguageDto>> getLanguages() {
    return ResponseEntity.ok(judgeService.getLanguages());
  }

  @GetMapping("/submission/{token}")
  @Operation(summary = "get submission results")
  @ApiResponses(
      value = { @ApiResponse(responseCode = "200", description = "Submission results"),
          @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<List<SubmissionResultDto>> getSubmissionResult(@PathVariable String token) {
    return ResponseEntity.ok(judgeService.getSubmissionResult(token));
  }

  @PostMapping("/submission")
  @Operation(summary = "submit solution")
  @ApiResponses(
      value = { @ApiResponse(responseCode = "200", description = "Solution submitted"),
          @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<SubmissionResponseDto> submitSolution(
      @Valid @RequestBody SubmissionDto submission) {
    return ResponseEntity.ok(judgeService.submit(submission));
  }
}
