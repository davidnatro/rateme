package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.CreateRoleDto;
import me.rate.rateme.data.model.RoleModel;
import me.rate.rateme.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "role endpoints")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "get all roles")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Page of roles"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access") })
    public ResponseEntity<Page<RoleModel>> getAllPageable(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(roleService.findAll(pageable));
    }

    @GetMapping("/{name}")
    @Operation(summary = "get role by name")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Role not found") })
    public ResponseEntity<RoleModel> getByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }

    @PostMapping
    @Operation(summary = "create role")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "409", description = "Role already exists") })
    public ResponseEntity<RoleModel> createRole(@Valid @RequestBody CreateRoleDto request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "delete role")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access") })
    public ResponseEntity<Void> deleteRole(@PathVariable String name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
