package com.rest.api.controllers;

import com.rest.api.Services.PersonServices;
import com.rest.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/{id}")
    public Person findById(@PathVariable(value = "id") Long id){
        return personServices.findById(id);
    }

    @GetMapping
    public List<Person> findAll(){
        return personServices.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person){
        Person savedPerson = personServices.create(person);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPerson.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Person person){
        personServices.update(person);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
