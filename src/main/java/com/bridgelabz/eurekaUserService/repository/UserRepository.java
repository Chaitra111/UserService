package com.bridgelabz.eurekaUserService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.eurekaUserService.model.User;

/**
 * @author Chaitra Ankolekar
 * Purpose :Repository which extends MongoRepository which already have built-in all CRUD operations
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByemailId(String mailId);

	//void save(RegistrationModel reg);

	//void save(Optional<User> user);
	
	User findByUserId(String id);
}
