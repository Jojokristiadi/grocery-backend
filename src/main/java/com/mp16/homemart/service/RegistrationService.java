package com.mp16.homemart.service;

import com.mp16.homemart.dto.RegistrationRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(RegistrationRequestDTO request) {
        return "Horay register completed";
    }
}
