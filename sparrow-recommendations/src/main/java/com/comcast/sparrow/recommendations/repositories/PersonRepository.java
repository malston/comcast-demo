package com.comcast.sparrow.recommendations.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.comcast.sparrow.recommendations.domain.Person;

public interface PersonRepository extends GraphRepository<Person> {
    Person findByUserName(String userName);
}
