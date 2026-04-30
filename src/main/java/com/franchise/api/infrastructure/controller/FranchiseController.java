package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.usecase.CreateBranchUseCase;
import com.franchise.api.application.usecase.CreateFranchiseUseCase;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.controller.dto.CreateBranchRequest;
import com.franchise.api.infrastructure.controller.dto.CreateFranchiseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final CreateBranchUseCase createBranchUseCase;

    public FranchiseController(
            CreateFranchiseUseCase createFranchiseUseCase,
            CreateBranchUseCase createBranchUseCase
    ) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.createBranchUseCase = createBranchUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Franchise>> create(
            @Valid @RequestBody CreateFranchiseRequest request) {

        Franchise franchise = new Franchise(null, request.getName());

        return createFranchiseUseCase.execute(franchise)
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
}
