package com.franchise.api.infrastructure.controller;

import com.franchise.api.application.usecase.CreateFranchiseUseCase;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infrastructure.controller.dto.CreateFranchiseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {
    private final CreateFranchiseUseCase createFranchiseUseCase;

    public FranchiseController(CreateFranchiseUseCase createFranchiseUseCase){
        this.createFranchiseUseCase = createFranchiseUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Franchise>> create(@Valid @RequestBody CreateFranchiseRequest request){
        Franchise franchise = new Franchise(null , request.getName());
        return createFranchiseUseCase.execute(franchise).map(ResponseEntity::ok);
    }
}
