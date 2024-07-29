package com.pier.filetransfer.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class File implements Serializable{
	private static final long serialVersionUID = 2048636232121203283L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String name;
	@Column
	private String url;
	@Column
	private Date createdAt;
	
	public File() {}
	
	public File(String name, String url, Date createdAt) {
		this.name = name; 
		this.url = url;
		this.createdAt = createdAt;
	}
}
