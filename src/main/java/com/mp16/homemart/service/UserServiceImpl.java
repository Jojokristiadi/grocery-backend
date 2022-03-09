package com.mp16.homemart.service;

import com.mp16.homemart.model.Role;
import com.mp16.homemart.model.User;
import com.mp16.homemart.repository.UserRepository;
import com.mp16.homemart.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService{

//    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto){
        User user = new User(
                registrationDto.getName(),
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                Arrays.asList(new Role("ROLE_USER"))
        );
        return userRepository.save(user);
    }
}
