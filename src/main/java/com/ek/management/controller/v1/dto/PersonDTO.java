package com.ek.management.controller.v1.dto;

import com.ek.management.model.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {

    @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY,
            description = "Person ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @NotNull(message = "'name' must not be null")
    @Schema(name = "name", description = "Persons name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "'identifier' must not be null")
    @Schema(name = "identifier", description = "Person identifier",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String identifier;

    @Schema(name = "identifierType", accessMode = Schema.AccessMode.READ_ONLY,
            description = "Identifier type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String identifierType;

    public Person toDomain() {
        return Person.builder()
                .name(this.name)
                .identifier(this.identifier)
                .build();
    }

    public static PersonDTO from(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .identifier(person.getIdentifier())
                .identifierType(person.getIdentifierType().name())
                .build();
    }
}
