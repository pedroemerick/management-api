package com.ek.management.service;

import com.ek.management.exception.InvalidIdentifierException;
import com.ek.management.model.IdentifierType;
import com.ek.management.model.Person;
import com.ek.management.repository.PersonRepository;
import com.ek.management.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testRegisterPerson() {
        var personSaved = Person.builder()
                .id(1L).name("anyName")
                .identifier("01234567890").identifierType(IdentifierType.CPF)
                .build();
        when(this.personRepository.save(any(Person.class))).thenReturn(personSaved);

        var personMock = Person.builder().name("anyName")
                .identifier("01234567890").build();
        var person = this.personService.register(personMock);

        assertThat(person, is(equalTo(person)));

        verify(this.personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testGetIdentifierTypeWithCpf() {
        var identifierType = this.personService.getIdentifierTypeByIdentifier("01234567890");
        assertThat(identifierType, is(equalTo(IdentifierType.CPF)));
    }

    @Test
    void testGetIdentifierTypeWithCnpj() {
        var identifierType = this.personService.getIdentifierTypeByIdentifier("01234567890123");
        assertThat(identifierType, is(equalTo(IdentifierType.CNPJ)));
    }

    @Test
    void testGetIdentifierTypeWithInvalidIdentifier() {
        assertThrows(InvalidIdentifierException.class, () ->
                this.personService.getIdentifierTypeByIdentifier("123"));
    }
}
