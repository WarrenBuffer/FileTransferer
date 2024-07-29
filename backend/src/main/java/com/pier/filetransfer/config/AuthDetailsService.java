package com.pier.filetransfer.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pier.filetransfer.model.User;
import com.pier.filetransfer.repository.UserRepository;

@Service
public class AuthDetailsService implements UserDetailsService {
    private UserRepository repo;

    public AuthDetailsService(UserRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> a = repo.findByUsername(username);
        if (a.isPresent()) {
            return org.springframework.security.core.userdetails.User.withUsername(a.get().getUsername())
                .password(a.get().getPassword())
                .build();
        }
        
        throw new UsernameNotFoundException(username);
    }
}
