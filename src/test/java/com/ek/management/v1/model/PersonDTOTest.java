package com.ek.management.v1.model;

import com.ek.management.v1.controller.dto.PersonDTO;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class PersonDTOTest {

    @Test
    void testPersonDtoToDomain() {
        var personDTO = PersonDTO.builder()
                .name("anyName")
                .identifier("anyIdentifier")
                .build();

        var person = personDTO.toDomain();

        assertThat(person.getName(), is(equalTo(personDTO.getName())));
        assertThat(person.getIdentifier(), is(equalTo(personDTO.getIdentifier())));
    }

    @Test
    void testPersonDtoFromDomain() {
        var person = Person.builder()
                .id(1L)
                .name("anyName")
                .identifier("anyIdentifier")
                .identifierType(IdentifierType.CNPJ)
                .build();

        var personDTO = PersonDTO.from(person);

        assertThat(personDTO.getId(), is(equalTo(person.getId())));
        assertThat(personDTO.getName(), is(equalTo(person.getName())));
        assertThat(personDTO.getIdentifier(), is(equalTo(person.getIdentifier())));
        assertThat(personDTO.getIdentifierType(), is(equalTo(person.getIdentifierType().name())));
    }
}
