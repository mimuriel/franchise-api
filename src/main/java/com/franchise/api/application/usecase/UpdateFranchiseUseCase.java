package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public UpdateFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(String franchiseId, String name) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franchise -> {
                    franchise.setName(name);
                    return franchiseRepository.save(franchise);
                });
    }
}