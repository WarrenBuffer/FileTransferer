package com.pier.filetransfer.service;

import java.util.List;
import java.util.Optional;

import com.pier.filetransfer.model.File;

public interface FileService {
	File saveFile(File file);
	List<File> findAll();
	void deleteFile(File file);
	Optional<File> findById(long id);
	List<File> findByUsername(String username);
}
