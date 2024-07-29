package com.pier.filetransfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pier.filetransfer.model.UserFile;
import com.pier.filetransfer.repository.UserFileRepository;
import com.pier.filetransfer.service.UserFileService;

@Service
public class UserFileServiceImpl implements UserFileService {
	@Autowired
	UserFileRepository repo;

	@Override
	public void saveUserFile(UserFile userFile) {
		repo.save(userFile);
	}

	@Override
	public List<UserFile> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteUserModelFile(UserFile userFile) {
		repo.delete(userFile);
	}

	@Override
	public void deleteById(long id) {
		repo.deleteById(id);
	}

	@Override
	public Optional<UserFile> findById(long id) {
		return repo.findById(id);
	}

	@Override
	public List<UserFile> findByUsername(String username) {
		return repo.findByUsername(username);
	}
}
