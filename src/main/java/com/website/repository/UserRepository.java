package com.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;
import com.website.model.*;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	Optional<User> findUserByEmail(String email);

}
