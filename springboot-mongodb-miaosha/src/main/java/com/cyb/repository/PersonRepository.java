package com.cyb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cyb.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

	Person findByName(String name);

	@Query("{'age': { '$lt' : ?0}}")
	List<Person> withQueryFindByAge(Integer age);

}
