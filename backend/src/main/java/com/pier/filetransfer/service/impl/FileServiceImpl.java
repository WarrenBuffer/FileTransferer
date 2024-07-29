package com.pier.filetransfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pier.filetransfer.model.File;
import com.pier.filetransfer.repository.FileRepository;
import com.pier.filetransfer.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileRepository repo;

	@Override
	public File saveFile(File file) {
		return repo.save(file);
	}

	@Override
	public List<File> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteFile(File file) {
		repo.delete(file);
	}

	@Override
	public Optional<File> findById(long id) {
		return repo.findById(id);
	}

	@Override
	public List<File> findByUsername(String username) {
		return repo.findByUsername(username);
	}
}
