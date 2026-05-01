package com.franchise.api.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateFranchiseRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
}
