package com.franchise.api.application.usecase;

import com.franchise.api.application.dto.TopProductDTO;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Product;
import com.franchise.api.infrastructure.persistence.BranchRepository;
import com.franchise.api.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class GetTopProductsUseCaseTest {

    private final BranchRepository branchRepository =
            Mockito.mock(BranchRepository.class);

    private final ProductRepository productRepository =
            Mockito.mock(ProductRepository.class);

    private final GetTopProductsUseCase useCase =
            new GetTopProductsUseCase(branchRepository, productRepository);

    @Test
    void shouldReturnProductWithHighestStockPerBranch() {

        String franchiseId = "fr-001";

        Branch branch = new Branch("br-01", "Sucursal Laureles", franchiseId);

        Product product1 = new Product("p-01", "Teclado mecánico", 12, "br-01");
        Product product2 = new Product("p-02", "Monitor 27 pulgadas", 45, "br-01");

        when(branchRepository.findByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch));

        when(productRepository.findByBranchId("br-01"))
                .thenReturn(Flux.just(product1, product2));

        StepVerifier.create(useCase.execute(franchiseId))
                .expectNextMatches(result ->
                        result.getBranchName().equals("Sucursal Laureles")
                                && result.getProductName().equals("Monitor 27 pulgadas")
                                && result.getStock().equals(45)
                )
                .verifyComplete();
    }
}