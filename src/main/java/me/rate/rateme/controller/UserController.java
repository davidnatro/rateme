package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.CreateUserDto;
import me.rate.rateme.data.dto.UpdateUserDto;
import me.rate.rateme.data.model.UserModel;
import me.rate.rateme.service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "user endpoints")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "get all users")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Page of users"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access") })
    public ResponseEntity<Page<UserModel>> getAllPageable(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{username}")
    @Operation(summary = "get user by username")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found") })
    public ResponseEntity<UserModel> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping
    @Operation(summary = "create user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created user"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "409", description = "User already exists") })
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody CreateUserDto request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{username}")
    @Operation(summary = "update user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created user"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found") })
    public ResponseEntity<UserModel> updateUser(@PathVariable String username,
                                                @Valid @RequestBody UpdateUserDto request) {
        return ResponseEntity.ok(userService.updateByUsername(username, request));
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "delete user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created user"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access") })
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
