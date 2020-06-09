package com.beth.infy.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorizationService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if ("bethapudi".equals(s)) {
            return new User("bethapudi", "$2y$12$ogUPBhfGzj2mkpdA39.Mq.1L4dAXdZnnsAlUS.xidv9s7btorGobK", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }
}
