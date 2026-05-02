package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllFranchisesUseCase {
    private final FranchiseRepository franchiseRepository;

    public GetAllFranchisesUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Flux<Franchise> execute() {
        return franchiseRepository.findAll();
    }

}
