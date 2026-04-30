package com.franchise.api.application.usecase;

import com.franchise.api.application.dto.TopProductDTO;
import com.franchise.api.infrastructure.persistence.BranchRepository;
import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetTopProductsUseCase {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public GetTopProductsUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    public Flux<TopProductDTO> execute(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId).flatMap(branch -> productRepository.findByBranchId(branch.getId()).sort((p1, p2) -> p2.getStock() - p1.getStock()).next().map(product -> new TopProductDTO(branch.getName(), product.getName(), product.getStock())));
    }
}
