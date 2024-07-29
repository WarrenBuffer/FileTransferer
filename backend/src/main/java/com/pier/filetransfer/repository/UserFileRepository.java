package com.pier.filetransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pier.filetransfer.model.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile, Long>{
	 @Query(value = "select * from user_files where username = ?1", nativeQuery = true)
	 List<UserFile> findByUsername(String username);
}
