package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@Tag(name = "Comapny", description = "company endpoints")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "get all companies")
    public ResponseEntity<Page<CompanyModel>> getAllPageable(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(companyService.findAll(pageable));
    }

    @GetMapping("/{name}")
    @Operation(summary = "get company by name")
    public ResponseEntity<CompanyModel> getByName(@PathVariable String name) {
        return ResponseEntity.ok(companyService.findByName(name));
    }

    @PostMapping
    @Operation(summary = "create company")
    public ResponseEntity<CompanyModel> createCompany(
            @Valid @RequestBody CreateCompanyDto request) {
        return ResponseEntity.ok(companyService.create(request));
    }

    @PatchMapping("/{name}/hire/{username}")
    @Operation(summary = "hire employee")
    public ResponseEntity<Void> hireEmployee(@PathVariable String name,
                                             @PathVariable String username) {
        companyService.hireEmployee(name, username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}/fire/{username}")
    @Operation(summary = "fire employee")
    public ResponseEntity<Void> fireEmployee(@PathVariable String name,
                                             @PathVariable String username) {
        companyService.fireEmployee(name, username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{name}")
    @Operation(summary = "update company")
    public ResponseEntity<CompanyModel> updateCompany(@PathVariable String name,
                                                      @Valid @RequestBody UpdateCompanyDto request) {
        return ResponseEntity.ok(companyService.updateByName(name, request));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "delete company")
    public ResponseEntity<Void> deleteCompany(@PathVariable String name) {
        companyService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
