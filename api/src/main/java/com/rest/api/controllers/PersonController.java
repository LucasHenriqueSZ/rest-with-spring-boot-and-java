package com.rest.api.controllers;

import com.rest.api.Services.PersonServices;
import com.rest.api.data.dto.v1.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PersonDTO findById(@PathVariable(value = "id") Long id){
        return personServices.findById(id);
    }

    @GetMapping
    public List<PersonDTO> findAll(){
        return personServices.findAll();
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO){
        PersonDTO savedPersonDTO = personServices.create(personDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPersonDTO.getId()).toUri();

        return ResponseEntity.created(location).body(savedPersonDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody PersonDTO personDTO){
        PersonDTO updatePersonDTO = personServices.update(personDTO);
        return  ResponseEntity.ok().body(updatePersonDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
