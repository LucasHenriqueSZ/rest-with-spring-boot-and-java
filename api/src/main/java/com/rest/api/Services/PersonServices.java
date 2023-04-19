package com.rest.api.Services;

import com.rest.api.data.dto.v1.PersonDTO;
import com.rest.api.data.dto.v2.PersonDTOv2;
import com.rest.api.exceptions.ResourceNotFoundException;
import com.rest.api.mapper.ModelMapper;
import com.rest.api.model.Person;
import com.rest.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    public PersonDTO findById(Long id){
        logger.info("Find one person by id: " + id);
        Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        return ModelMapper.parseObject(person,PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("Find all persons");
        return ModelMapper.parseListObjects(repository.findAll(),PersonDTO.class);
    }

    public List<PersonDTOv2> findAllv2(){
        logger.info("Find all persons with v2");
        return ModelMapper.parseListObjects(repository.findAll(),PersonDTOv2.class);
    }

    public PersonDTO create(PersonDTO personDTO){
        logger.info("Create a new person");
        Person person = ModelMapper.parseObject(personDTO,Person.class);
        return ModelMapper.parseObject(repository.save(person),PersonDTO.class);
    }

    public PersonDTOv2 createv2(PersonDTOv2 personDTO){
        logger.info("Create a new person with v2");
        Person person = ModelMapper.parseObject(personDTO,Person.class);
        return ModelMapper.parseObject(repository.save(person),PersonDTOv2.class);
    }

    public PersonDTO update(PersonDTO personDTO){
        logger.info("update a person");
       Person entity = repository.findById(personDTO.getId())
               .orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));

       entity.setFirstName(personDTO.getFirstName());
       entity.setLastName(personDTO.getLastName());
       entity.setAddress(personDTO.getAddress());
       entity.setGender(personDTO.getGender());

        return ModelMapper.parseObject(repository.save(entity),PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("delete one person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        repository.delete(entity);
    }
}
