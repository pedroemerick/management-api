package com.ek.management.v1.controller;

import com.ek.management.v1.exception.InvalidIdentifierException;
import com.ek.management.v1.model.IdentifierType;
import com.ek.management.v1.model.Person;
import com.ek.management.v1.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:request/personRegisterRequest.json")
    private Resource personRegisterRequestResource;

    @Value("classpath:request/personRegisterRequestWithoutRequiredField.json")
    private Resource personRegisterRequestWithoutRequiredField;

    @Test
    void testRegisterPerson() throws Exception {
        final var request = StreamUtils.copyToString(
                this.personRegisterRequestResource.getInputStream(),
                StandardCharsets.UTF_8);

        final var personMock = Person.builder()
                .id(1L)
                .name("anyName")
                .identifier("01234567890")
                .identifierType(IdentifierType.CPF)
                .build();

        when(this.personService.register(any(Person.class)))
                .thenReturn(personMock);

        this.mockMvc.perform(
                        post("/v1/persons")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(equalTo(personMock.getId())), Long.class))
                .andExpect(jsonPath("$.name", is(equalTo(personMock.getName()))))
                .andExpect(jsonPath("$.identifier",
                        is(equalTo(personMock.getIdentifier()))))
                .andExpect(jsonPath("$.identifierType",
                        is(equalTo(personMock.getIdentifierType().name()))));

        verify(this.personService, times(1)).register(any(Person.class));
    }

    @Test
    void testRegisterPersonWithInvalidBody() throws Exception {
        final var request = StreamUtils.copyToString(
                this.personRegisterRequestWithoutRequiredField.getInputStream(),
                StandardCharsets.UTF_8);

        this.mockMvc.perform(
                        post("/v1/persons")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(equalTo(400))))
                .andExpect(jsonPath("$.title", is(equalTo("One or more invalid fields"))))
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]",
                        is(equalTo("'identifier' must not be null"))));

        verify(this.personService, times(0)).register(any(Person.class));
    }

    @Test
    void testRegisterPersonWithInvalidIdentifier() throws Exception {
        final var request = StreamUtils.copyToString(
                this.personRegisterRequestResource.getInputStream(),
                StandardCharsets.UTF_8);

        var exception = new InvalidIdentifierException();
        when(this.personService.register(any(Person.class)))
                .thenThrow(exception);

        this.mockMvc.perform(
                        post("/v1/persons")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(equalTo(409))))
                .andExpect(jsonPath("$.title", is(equalTo("Business rule violation"))))
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]",
                        is(equalTo(exception.getMessage()))));

        verify(this.personService, times(1)).register(any(Person.class));
    }

    @Test
    void testRegisterPersonWithUnexpectedError() throws Exception {
        final var request = StreamUtils.copyToString(
                this.personRegisterRequestResource.getInputStream(),
                StandardCharsets.UTF_8);

        when(this.personService.register(any(Person.class)))
                .thenThrow(NullPointerException.class);

        this.mockMvc.perform(
                        post("/v1/persons")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(equalTo(500))))
                .andExpect(jsonPath("$.title", is(equalTo("Unexpected error"))))
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]",
                        is(equalTo("Unexpected error, contact application admin."))));

        verify(this.personService, times(1)).register(any(Person.class));
    }
}
