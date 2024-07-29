package com.pier.filetransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pier.filetransfer.model.File;

public interface FileRepository extends JpaRepository<File, Long>{
	@Query(value = "select distinct f.id, f.created_at, f.name, f.url from files f, user_files uf where uf.username = ?1", nativeQuery = true)
	List<File> findByUsername(String username);
}
