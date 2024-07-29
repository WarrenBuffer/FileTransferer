package com.pier.filetransfer.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 4381513407287841348L;
	
	@Id
    private String username;
    private Integer age;
    private Boolean enabled;
    private String name;
    private String password;
    private String role;
    private String surname;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFile> userFiles;
}
