package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateProductStockUseCase {

    private final ProductRepository productRepository;

    public UpdateProductStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(String productId, Integer stock) {
        return productRepository.findById(productId).switchIfEmpty(Mono.error(new RuntimeException("El producto no existe"))).flatMap(product -> {
            product.setStock(stock);
            return productRepository.save(product);
        });
    }
}