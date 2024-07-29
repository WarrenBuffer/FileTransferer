package com.pier.filetransfer.service;

import java.util.List;
import java.util.Optional;

import com.pier.filetransfer.model.User;

public interface UserService {
	void saveUser(User user);
	List<User> findAll();
	void deleteUser(User user);
	Optional<User> findById(String username);
}
