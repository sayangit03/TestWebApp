package com.spring.microservices.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.microservices.bean.Student;

public interface MyRepository extends MongoRepository<Student, Integer> {

}
