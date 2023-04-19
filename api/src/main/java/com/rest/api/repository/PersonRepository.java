package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
    
}
