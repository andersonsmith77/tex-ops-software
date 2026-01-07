package com.texops.app.dto;

public record EmployeeDTO(
        Long id,
        String code,
        String name,
        Boolean active
) { }
