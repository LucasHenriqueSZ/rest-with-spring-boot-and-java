package com.rest.api.Services;

import com.rest.api.exceptions.ResourceNotFoundException;
import com.rest.api.model.Person;
import com.rest.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    //add dependency injection to repository PersonRepository
    @Autowired
    private PersonRepository repository;

    public Person findById(Long id){
        logger.info("Find one person by id: " + id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
    }

    public List<Person> findAll(){
        logger.info("Find all persons");
        return repository.findAll();
    }

    public Person create(Person person){
        logger.info("Create a new person");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("update a person");
       Person entity = repository.findById(person.getId())
               .orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));

       entity.setLastName(person.getFirstName());
       entity.setLastName(person.getLastName());
       entity.setAddress(person.getAddress());
       entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id){
        logger.info("delete one person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        repository.delete(entity);
    }
}
