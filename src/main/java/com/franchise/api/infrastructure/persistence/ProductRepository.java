package com.franchise.api.infrastructure.persistence;

import com.franchise.api.domain.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByBranchId(String branchId);
}
