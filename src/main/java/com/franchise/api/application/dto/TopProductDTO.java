package com.franchise.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductDTO {
    private String branchName;
    private String productName;
    private Integer stock;
}