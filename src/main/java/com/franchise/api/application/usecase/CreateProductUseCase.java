package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.persistence.BranchRepository;
import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public CreateProductUseCase(ProductRepository productRepository, BranchRepository branchRepository) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }

    public Mono<Product> execute(String branchId, Product product) {
        return branchRepository.findById(branchId).switchIfEmpty(Mono.error(new RuntimeException("La sucursal no existe"))).flatMap(branch -> {
            product.setBranchId(branchId);
            return productRepository.save(product);
        });
    }
}