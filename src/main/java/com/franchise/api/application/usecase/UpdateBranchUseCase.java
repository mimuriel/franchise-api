package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Branch;
import com.franchise.api.infrastructure.persistence.BranchRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateBranchUseCase {
    private final BranchRepository branchRepository;

    public UpdateBranchUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Mono<Branch> execute(String branchId, String name) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    branch.setName(name);
                    return branchRepository.save(branch);
                });
    }
}
