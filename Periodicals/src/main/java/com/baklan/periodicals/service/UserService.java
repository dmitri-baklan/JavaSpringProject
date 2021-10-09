package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.UserDTO;
import com.baklan.periodicals.entity.user.Role;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.entity.user.details.UserDetailsImpl;
import com.baklan.periodicals.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {

        return new UserDetailsImpl(userRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException(String.format("email %s not found", email))));
    }

    public UserDTO getUserByEmailAuth(UserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        Objects.requireNonNull(user);

        return UserDTO
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public void signUpUser(UserDTO userDTO) {

        String encPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        User user = User
                .builder()
                .email(userDTO.getEmail())
                .password(encPassword)
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .role(Role.valueOf(userDTO.getRole()))
                .build();

        userRepository.save(user);
    }
}
