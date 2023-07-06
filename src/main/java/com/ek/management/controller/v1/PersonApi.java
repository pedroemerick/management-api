package com.ek.management.controller.v1;

import com.ek.management.controller.v1.dto.ErrorDTO;
import com.ek.management.controller.v1.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Persons")
@RequestMapping("/v1")
public interface PersonApi {

    @Operation(summary = "Register", description = "Register a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered person",
                    content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Business rule violation",
                    content = {@Content(schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = {@Content(schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(value = "/persons",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    default ResponseEntity<PersonDTO> registerPerson(@Valid @RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
