package com.ek.management.v1.service;

import com.ek.management.v1.exception.InvalidIdentifierException;
import com.ek.management.v1.model.IdentifierType;
import com.ek.management.v1.model.Person;
import com.ek.management.v1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person register(Person person) {
        person.setIdentifierType(this.getIdentifierTypeByIdentifier(person.getIdentifier()));

        return this.personRepository.save(person);
    }

    public IdentifierType getIdentifierTypeByIdentifier(String identifier) {
        if (identifier.length() == IdentifierType.CPF.length) {
            return IdentifierType.CPF;
        } else if (identifier.length() == IdentifierType.CNPJ.length) {
            return IdentifierType.CNPJ;
        } else {
            throw new InvalidIdentifierException();
        }
    }
}
