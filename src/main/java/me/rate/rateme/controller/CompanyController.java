package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.model.CompanyModel;
import me.rate.rateme.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@Tag(name = "Company", description = "company endpoints")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "get all companies")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Page of companies"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access") })
    public ResponseEntity<Page<CompanyModel>> getAllPageable(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(companyService.findAll(pageable));
    }

    @GetMapping("/{name}")
    @Operation(summary = "get company by name")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Company"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Company not found") })
    public ResponseEntity<CompanyModel> getByName(@PathVariable String name) {
        return ResponseEntity.ok(companyService.findByName(name));
    }

    @PostMapping
    @Operation(summary = "create company")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created company"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "409", description = "Company already exists") })
    public ResponseEntity<CompanyModel> createCompany(
            @Valid @RequestBody CreateCompanyDto request) {
        return ResponseEntity.ok(companyService.create(request));
    }

    @PutMapping("/{companyName}")
    @Operation(summary = "update company")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated company"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "404", description = "Company not found") })
    public ResponseEntity<CompanyModel> updateCompany(@PathVariable String companyName,
                                                      @Valid @RequestBody UpdateCompanyDto request) {
        return ResponseEntity.ok(companyService.updateByName(companyName, request));
    }

    @PostMapping("/{companyName}/employee")
    @Operation(summary = "hire employee")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hired employee"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "404", description = "Company or user not found") })
    public ResponseEntity<Void> hireEmployee(@PathVariable String companyName,
                                             @RequestParam String username) {
        companyService.hireEmployee(companyName, username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "delete company")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted company"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "404", description = "Company not found") })
    public ResponseEntity<Void> deleteCompany(@PathVariable String name) {
        companyService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{companyName}/employee")
    @Operation(summary = "fire employee")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fired employee"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "404", description = "Company or user not found") })
    public ResponseEntity<Void> fireEmployee(@PathVariable String companyName,
                                             @RequestParam String username) {
        companyService.fireEmployee(companyName, username);
        return ResponseEntity.noContent().build();
    }
}
