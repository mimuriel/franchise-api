package com.franchise.api.infrastructure.persistence;

import com.franchise.api.domain.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
}
