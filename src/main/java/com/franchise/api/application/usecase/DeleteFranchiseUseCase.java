package com.franchise.api.application.usecase;

import com.franchise.api.infrastructure.persistence.BranchRepository;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public DeleteFranchiseUseCase(
            FranchiseRepository franchiseRepository,
            BranchRepository branchRepository
    ) {
        this.franchiseRepository = franchiseRepository;
        this.branchRepository = branchRepository;
    }

    public Mono<Void> execute(String franchiseId) {
        return branchRepository.existsByFranchiseId(franchiseId)
                .flatMap(hasBranches -> {
                    if (hasBranches) {
                        return Mono.error(new IllegalStateException(
                                "Este registro cuenta con elementos vinculados y no puede ser eliminado."
                        ));
                    }

                    return franchiseRepository.deleteById(franchiseId);
                });
    }
}