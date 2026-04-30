package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.usecase.CreateProductUseCase;
import com.franchise.api.application.usecase.DeleteProductUseCase;
import com.franchise.api.application.usecase.UpdateProductStockUseCase;
import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.controller.dto.CreateProductRequest;
import com.franchise.api.infrastructure.controller.dto.UpdateStockRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branches")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;

    public ProductController(CreateProductUseCase createProductUseCase, DeleteProductUseCase deleteProductUseCase, UpdateProductStockUseCase updateProductStockUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductStockUseCase = updateProductStockUseCase;
    }

    @PostMapping("/{branchId}/products")
    public Mono<ResponseEntity<Product>> addProduct(@PathVariable String branchId, @Valid @RequestBody CreateProductRequest request) {
        Product product = new Product(null, request.getName(), request.getStock(), branchId);

        return createProductUseCase.execute(branchId, product).map(ResponseEntity::ok);
    }

    @DeleteMapping("/products/{productId}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String productId) {
        return deleteProductUseCase.execute(productId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping("/products/{productId}/stock")
    public Mono<ResponseEntity<Product>> updateStock(
            @PathVariable String productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return updateProductStockUseCase.execute(productId, request.getStock())
                .map(ResponseEntity::ok);
    }
}