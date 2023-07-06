package com.ek.management.v1.repository;

import com.ek.management.v1.model.IdentifierType;
import com.ek.management.v1.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSavePerson() {
        var personMock = Person.builder()
                .name("anyName")
                .identifier("anyIdentifier")
                .identifierType(IdentifierType.CNPJ)
                .build();

        var person = this.personRepository.save(personMock);

        assertThat(person.getId(), is(notNullValue()));
        assertThat(person.getName(), is(equalTo(personMock.getName())));
        assertThat(person.getIdentifier(), is(equalTo(personMock.getIdentifier())));
        assertThat(person.getIdentifierType(), is(equalTo(personMock.getIdentifierType())));
    }
}
