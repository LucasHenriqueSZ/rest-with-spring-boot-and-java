package com.rest.api.unittests.mockito.services;

import com.rest.api.Services.PersonServices;
import com.rest.api.data.dto.v1.PersonDTO;
import com.rest.api.exceptions.RequiredObjectIsNullException;
import com.rest.api.model.Person;
import com.rest.api.repository.PersonRepository;
import com.rest.api.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFinById() {
        Person entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PersonDTO result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testFindAll() {
        List<Person> personList = input.mockEntityList();

        when(repository.findAll()).thenReturn(personList);

        List<PersonDTO> result = service.findAll();
        assertNotNull(result);
        assertEquals(14, result.size());

        PersonDTO person1 = result.get(1);
        assertNotNull(person1);
        assertNotNull(person1.getId());
        assertNotNull(person1.getLinks());
        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", person1.getFirstName());
        assertEquals("Last Name Test1", person1.getLastName());
        assertEquals("Addres Test1", person1.getAddress());
        assertEquals("Female", person1.getGender());

        PersonDTO person6 = result.get(6);
        assertNotNull(person6);
        assertNotNull(person6.getId());
        assertNotNull(person6.getLinks());
        assertTrue(person6.toString().contains("links: [</api/v1/person/6>;rel=\"self\"]"));
        assertEquals("First Name Test6", person6.getFirstName());
        assertEquals("Last Name Test6", person6.getLastName());
        assertEquals("Addres Test6", person6.getAddress());
        assertEquals("Male", person6.getGender());

        PersonDTO person13 = result.get(13);
        assertNotNull(person13);
        assertNotNull(person13.getId());
        assertNotNull(person13.getLinks());
        assertTrue(person13.toString().contains("links: [</api/v1/person/13>;rel=\"self\"]"));
        assertEquals("First Name Test13", person13.getFirstName());
        assertEquals("Last Name Test13", person13.getLastName());
        assertEquals("Addres Test13", person13.getAddress());
        assertEquals("Female", person13.getGender());
    }

    @Test
    void testCreate() {
        Person entity = input.mockEntity(1);
        PersonDTO dto = input.mockDTO(1);

        when(repository.save(entity)).thenReturn(entity);

        PersonDTO result = service.create(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdate() {
        Person entity = input.mockEntity(1);
        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        PersonDTO result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testDelete() {
        Person entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
