package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.dto.TopProductDTO;
import com.franchise.api.application.usecase.*;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.controller.dto.CreateBranchRequest;
import com.franchise.api.infrastructure.controller.dto.CreateFranchiseRequest;
import com.franchise.api.infrastructure.controller.dto.UpdateBranchRequest;
import com.franchise.api.infrastructure.controller.dto.UpdateFranchiseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final CreateBranchUseCase createBranchUseCase;
    private final GetTopProductsUseCase getTopProductsUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final UpdateBranchUseCase updateBranchUseCase;

    public FranchiseController(
            CreateFranchiseUseCase createFranchiseUseCase,
            CreateBranchUseCase createBranchUseCase,
            GetTopProductsUseCase getTopProductsUseCase,
            UpdateFranchiseUseCase updateFranchiseUseCase,
            UpdateBranchUseCase updateBranchUseCase
    ) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.createBranchUseCase = createBranchUseCase;
        this.getTopProductsUseCase = getTopProductsUseCase;
        this.updateFranchiseUseCase = updateFranchiseUseCase;
        this.updateBranchUseCase = updateBranchUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Franchise>> create(
            @Valid @RequestBody CreateFranchiseRequest request) {

        Franchise franchise = new Franchise(null, request.getName());

        return createFranchiseUseCase.execute(franchise)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<ResponseEntity<Franchise>> updateFranchiseName(
            @PathVariable String franchiseId,
            @Valid @RequestBody UpdateFranchiseRequest request) {
        return updateFranchiseUseCase.execute(franchiseId, request.getName())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{franchiseId}/branches")
    public Mono<ResponseEntity<Branch>> addBranch(
            @PathVariable String franchiseId,
            @Valid @RequestBody CreateBranchRequest request
    ) {
        Branch branch = new Branch(null, request.getName(), franchiseId);
        return createBranchUseCase.execute(franchiseId, branch)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/branches/{branchId}/name")
    public Mono<ResponseEntity<Branch>> updateFranchiseName(
            @PathVariable String branchId,
            @Valid @RequestBody UpdateBranchRequest request) {
        return updateBranchUseCase.execute(branchId, request.getName())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<TopProductDTO> getTopProducts(@PathVariable String franchiseId) {
        return getTopProductsUseCase.execute(franchiseId);
    }
}
