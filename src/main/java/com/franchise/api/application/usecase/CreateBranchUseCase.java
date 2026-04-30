package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Branch;
import com.franchise.api.infrastructure.persistence.BranchRepository;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateBranchUseCase {
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public CreateBranchUseCase(BranchRepository branchRepository, FranchiseRepository franchiseRepository) {
        this.branchRepository = branchRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Branch> execute(String franchiseId, Branch branch) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("La franquicia no existe")))
                .flatMap(franchise -> {
                    branch.setFranchiseId(franchiseId);
                    return branchRepository.save(branch);
                });
    }
}
