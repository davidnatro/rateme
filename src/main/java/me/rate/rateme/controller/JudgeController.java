package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.service.JudgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of languages"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<List<LanguageDto>> getLanguages() {
    return ResponseEntity.ok(judgeService.getLanguages());
  }
}
