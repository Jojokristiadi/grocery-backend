package com.mp16.homemart.service;

import com.mp16.homemart.model.User;
import com.mp16.homemart.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}