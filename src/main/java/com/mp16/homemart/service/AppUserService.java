package com.mp16.homemart.service;

import com.mp16.homemart.model.Category;
import com.mp16.homemart.model.User;
import com.mp16.homemart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email))
        );
    }

    public String signUpUser(User user){
        /*boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists){
            throw new IllegalStateException("Email sudah ada yang punya");
        }*/

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "redirect:/register?success";
    }

    public boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public User get(long id) {
        return userRepository.findById(id).get();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
