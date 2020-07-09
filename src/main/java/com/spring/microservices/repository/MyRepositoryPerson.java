package com.spring.microservices.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.microservices.bean.Person;

public interface MyRepositoryPerson extends MongoRepository<Person, Integer> {

}
