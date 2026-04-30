package com.franchise.api.application.usecase;

import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Void> execute(String productId) {
        return productRepository.findById(productId).switchIfEmpty(Mono.error(new RuntimeException("El producto no existe"))).flatMap(productRepository::delete);
    }
}
