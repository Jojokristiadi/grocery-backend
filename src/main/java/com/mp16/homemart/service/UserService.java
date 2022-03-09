package com.mp16.homemart.service;

import com.mp16.homemart.model.User;
import com.mp16.homemart.dto.UserRegistrationDto;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}