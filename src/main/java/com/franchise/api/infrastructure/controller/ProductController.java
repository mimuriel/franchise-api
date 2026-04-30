package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.usecase.CreateProductUseCase;
import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.controller.dto.CreateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branches")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping("/{branchId}/products")
    public Mono<ResponseEntity<Product>> addProduct(@PathVariable String branchId, @Valid @RequestBody CreateProductRequest request) {
        Product product = new Product(null, request.getName(), request.getStock(), branchId);

        return createProductUseCase.execute(branchId, product).map(ResponseEntity::ok);
    }
}