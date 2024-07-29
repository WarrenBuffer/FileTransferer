package com.pier.filetransfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pier.filetransfer.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "select * from users where username = binary ?1", nativeQuery = true)
	public Optional<User> findByUsername(String username);
}
