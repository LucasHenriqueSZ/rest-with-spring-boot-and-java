package com.rest.api.integrationtests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.configs.TestConfigs;
import com.rest.api.integrationtests.DTO.PersonDTO;
import com.rest.api.integrationtests.testcontainers.AbstractIntegrationTests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTests {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://restapi.com")
                .setBasePath("/api/v1/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(person)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .body()
                        .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("São Paulo", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("Danilo", createdPerson.getFirstName());
        assertEquals("Silva", createdPerson.getLastName());
    }


    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://api.com")
                .setBasePath("/api/v1/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(person)
                        .when()
                        .post()
                        .then()
                        .statusCode(403)
                        .extract()
                        .body()
                        .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://restapi.com")
                .setBasePath("/api/v1/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("São Paulo", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
        assertEquals("Danilo", persistedPerson.getFirstName());
        assertEquals("Silva", persistedPerson.getLastName());
    }


    @Test
    @Order(4)
    public void testFindByIdWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://api.com")
                .setBasePath("/api/v1/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        person.setFirstName("Danilo");
        person.setLastName("Silva");
        person.setAddress("São Paulo");
        person.setGender("Male");
    }

}
