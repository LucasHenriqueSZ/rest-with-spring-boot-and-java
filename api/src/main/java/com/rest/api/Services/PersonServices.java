package com.rest.api.Services;

import com.rest.api.controllers.PersonController;
import com.rest.api.data.dto.v1.PersonDTO;
import com.rest.api.exceptions.RequiredObjectIsNullException;
import com.rest.api.exceptions.ResourceNotFoundException;
import com.rest.api.mapper.ModelMapper;
import com.rest.api.model.Person;
import com.rest.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    public PersonDTO findById(Long id) {
        logger.info("Find one person by id: " + id);
        Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        PersonDTO DTO = ModelMapper.parseObject(person, PersonDTO.class);
        DTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return DTO;
    }

    public List<PersonDTO> findAll() {
        logger.info("Find all persons");
        List<PersonDTO> persons = ModelMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
        return persons;
    }

    public PersonDTO create(PersonDTO personDTO) {
        if (personDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Create a new person");
        Person person = ModelMapper.parseObject(personDTO, Person.class);
        PersonDTO DTO = ModelMapper.parseObject(repository.save(person), PersonDTO.class);
        DTO.add(linkTo(methodOn(PersonController.class).findById(DTO.getId())).withSelfRel());
        return DTO;
    }


    public PersonDTO update(PersonDTO personDTO) {
        if (personDTO == null) throw new RequiredObjectIsNullException();

        logger.info("update a person");
        Person entity = repository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        PersonDTO DTO = ModelMapper.parseObject(repository.save(entity), PersonDTO.class);
        DTO.add(linkTo(methodOn(PersonController.class).findById(DTO.getId())).withSelfRel());
        return DTO;
    }

    public void delete(Long id) {
        logger.info("delete one person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        repository.delete(entity);
    }
}
