package com.comcast.sparrow.recommendations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comcast.sparrow.recommendations.domain.Person;
import com.comcast.sparrow.recommendations.repositories.PersonRepository;

@RestController
@EnableOAuth2Resource
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public Iterable<Person> people() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "/people", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        personRepository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

}
