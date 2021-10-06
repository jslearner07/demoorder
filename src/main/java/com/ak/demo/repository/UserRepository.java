package com.ak.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ak.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUserName(String username);

}
