package me.rate.rateme.data.dto;

import java.util.List;

public record UpdateUserDto(String username, String password, List<String> roles) { }
