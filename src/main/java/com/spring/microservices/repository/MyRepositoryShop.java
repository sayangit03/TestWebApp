package com.spring.microservices.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.microservices.bean.Shop;

public interface MyRepositoryShop extends MongoRepository<Shop, Integer> {

}
