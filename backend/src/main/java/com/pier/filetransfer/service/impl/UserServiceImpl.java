package com.pier.filetransfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pier.filetransfer.model.User;
import com.pier.filetransfer.repository.UserRepository;
import com.pier.filetransfer.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repo;
	
	@Override
	public void saveUser(User user) {
		repo.save(user);
	}

	@Override
	public List<User> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteUser(User user) {
		repo.delete(user);
	}

	@Override
	public Optional<User> findById(String username) {
		return repo.findByUsername(username);
	}

}
