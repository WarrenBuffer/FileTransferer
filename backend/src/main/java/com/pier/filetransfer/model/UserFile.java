package com.pier.filetransfer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_files")
public class UserFile {
	@Id
	@JoinColumn(name = "file_id")
	private long id;

	@JoinColumn(name = "username")
	private String username;
	
	public UserFile() {}
	
	public UserFile(long id, String username) {
		this.username = username;
		this.id = id;
	}
}
