package com.franchise.api.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBranchRequest {
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String name;
}
