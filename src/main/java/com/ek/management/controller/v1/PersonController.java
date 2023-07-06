package com.ek.management.controller.v1;

import com.ek.management.controller.v1.dto.PersonDTO;
import com.ek.management.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController implements PersonApi {

    @Autowired
    private PersonService personService;

    @Override
    public ResponseEntity<PersonDTO> registerPerson(PersonDTO personDTO) {
        var person = this.personService.register(personDTO.toDomain());

        return ResponseEntity.status(HttpStatus.CREATED).body(PersonDTO.from(person));
    }
}
