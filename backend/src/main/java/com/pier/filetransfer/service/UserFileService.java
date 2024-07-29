package com.pier.filetransfer.service;

import java.util.List;
import java.util.Optional;

import com.pier.filetransfer.model.UserFile;

public interface UserFileService {
	void saveUserFile(UserFile userFile);
	List<UserFile> findAll();
	void deleteUserModelFile(UserFile userFile);
	void deleteById(long id);
	Optional<UserFile> findById(long id);
	List<UserFile> findByUsername(String username);
}
