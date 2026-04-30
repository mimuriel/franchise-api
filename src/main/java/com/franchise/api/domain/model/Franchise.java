package com.franchise.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor//Genera automáticamente: getters, setters,toString,equals
public class Franchise {
    @Id
    private String id;
    private String name;
}
