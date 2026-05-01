package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateProductNameUseCase {
    private final ProductRepository productRepository;

    public UpdateProductNameUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(String productId, String name) {
        return productRepository.findById(productId).switchIfEmpty(Mono.error(new RuntimeException("El producto no existe"))).flatMap(product -> {
            product.setName(name);
            return productRepository.save(product);
        });
    }
}
