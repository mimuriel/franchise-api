package com.franchise.api.infrastructure.persistence;

import com.franchise.api.domain.model.Branch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository extends ReactiveMongoRepository<Branch, String> {
    Flux<Branch> findByFranchiseId(String franchiseId);
}
