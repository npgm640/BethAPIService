package com.beth.infy.service;


import com.beth.infy.domain.UserDto;
import com.beth.infy.model.UserOrm;
import com.beth.infy.repository.IUserRepository;
import com.beth.infy.util.CommonConstants;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorizationService implements UserDetailsService {


    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserOrm user = userRepository.findByUserName(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                new ArrayList<>());
    }

    public UserOrm getUser(long userId) {
        return userRepository.findByUserId(userId);
    }


    public UserOrm save (UserDto user) {
        //TODO - need to optimise the ORM with Abstract behavior
        UserOrm userOrm = new UserOrm();
        userOrm.setUserName(user.getUserName());
        userOrm.setPassword(bcryptEncoder.encode(user.getPassword()));
        userOrm.setFirstName(user.getFirstName());
        userOrm.setLastName(user.getLastName());
        userOrm.setEmail(user.getEmail());
        userOrm.setActive(CommonConstants.ACTIVE);
        userOrm.setCreatedBy(user.getUserName());
       userOrm.setCreatedTimestamp(DateTime.now().toString());
        userOrm.setLastModifiedBy(user.getUserName());
       userOrm.setLastModifiedDate(DateTime.now().toString());
        return (userRepository.save(userOrm));
    }
}
