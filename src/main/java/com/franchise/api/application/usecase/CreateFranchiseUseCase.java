package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateFranchiseUseCase {
    private final FranchiseRepository repository;

    public CreateFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public Mono<Franchise> execute(Franchise franchise) {
        return repository.save(franchise);
    }
}
