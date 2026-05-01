package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.usecase.CreateProductUseCase;
import com.franchise.api.application.usecase.DeleteProductUseCase;
import com.franchise.api.application.usecase.UpdateProductNameUseCase;
import com.franchise.api.application.usecase.UpdateProductStockUseCase;
import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.controller.dto.CreateProductRequest;
import com.franchise.api.infrastructure.controller.dto.UpdateNameProductRequest;
import com.franchise.api.infrastructure.controller.dto.UpdateStockProductRequest;
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
    private final UpdateProductNameUseCase updateProductNameUseCase;

    public ProductController(CreateProductUseCase createProductUseCase, DeleteProductUseCase deleteProductUseCase, UpdateProductStockUseCase updateProductStockUseCase, UpdateProductNameUseCase updateProductNameUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductStockUseCase = updateProductStockUseCase;
        this.updateProductNameUseCase = updateProductNameUseCase;
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
            @Valid @RequestBody UpdateStockProductRequest request
    ) {
        return updateProductStockUseCase.execute(productId, request.getStock())
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/products/{productId}/name")
    public Mono<ResponseEntity<Product>> updateName(
            @PathVariable String productId,
            @Valid @RequestBody UpdateNameProductRequest request
    ) {
        return updateProductNameUseCase.execute(productId, request.getName())
                .map(ResponseEntity::ok);
    }
}