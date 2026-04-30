package com.franchise.api.application.usecase;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.persistence.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class CreateFranchiseUseCaseTest {
    private final FranchiseRepository franchiseRepository = Mockito.mock(FranchiseRepository.class);

    private final CreateFranchiseUseCase useCase = new CreateFranchiseUseCase(franchiseRepository);

    @Test
    void shouldCreateFranchise() {

        Franchise franchise = new Franchise(null, "Punto tech");
        Franchise savedFranchise = new Franchise("1", "Punto tech");

        when(franchiseRepository.save(franchise))
                .thenReturn(Mono.just(savedFranchise));

        StepVerifier.create(useCase.execute(franchise))
                .expectNextMatches(result ->
                        result.getName().equals("Punto tech")
                )
                .verifyComplete();
    }
}
