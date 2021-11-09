package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.UserDTO;
import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.entity.user.Role;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.entity.user.details.UserDetailsImpl;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.exception.UserNotSavedException;
import com.baklan.periodicals.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public void changeIsActive(Long id)throws UserNotFoundException{
        User user = userRepository.findRedaerById(id)
                .orElseThrow(UserNotFoundException::new);

        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public User getUserByEmailAuth(UserDetails userDetails) throws UserNotFoundException{
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
    }


    public Page<User> getAllReaders(int page, int size, String searchQuery){
        Pageable pageable = buildPage("Name", true, page, size);
        return searchQuery.isBlank() ? userRepository.findByRole(Role.READER, pageable)
                : userRepository.findByEmail(searchQuery, pageable);
    }

    //TODO: throws??
    @Transactional
    public void signUpUser(UserDTO userDTO) throws UserNotFoundException {

        String encPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        User user = User
                .builder()
                .email(userDTO.getEmail())
                .password(encPassword)
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .role(Role.valueOf(userDTO.getRole()))
                .isActive(true)
                .balance(Role.valueOf(userDTO.getRole()).equals(Role.READER) ? 0L:null)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UserDTO userDTO) throws UserNotSavedException {
        User userToUpdate = userRepository.findByEmail(
                userDTO.getEmail()).orElseThrow(UserNotSavedException::new);

        String encPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
         userRepository.save(User.builder()
                .id(userToUpdate.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(encPassword)
                .periodicals(userToUpdate.getPeriodicals())
                .role(userToUpdate.getRole())
                .isActive(userToUpdate.isActive())
                .balance(userToUpdate.getBalance())
                .build());
    }

    private Pageable buildPage(String sortField, boolean asc, int page, int size) {
        Optional<Sort> sort = asc ? Optional.of(Sort.by(sortField).ascending())
                : Optional.of(Sort.by(sortField).descending());

        return PageRequest.of(page - 1, size, sort.get());
    }
}
