package com.baklan.periodicals.service;

import com.baklan.periodicals.controller.RegistrationController;
import com.baklan.periodicals.dto.ReplenishmentDTO;
import com.baklan.periodicals.entity.replenishment.Replenishment;
import com.baklan.periodicals.entity.user.Role;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.repository.ReplenishmentRepository;
import com.baklan.periodicals.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class ReplenishmentService {

    @Autowired
    private ReplenishmentRepository replenishmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void replenishBalance(UserDetails userDetails, ReplenishmentDTO replenishmentDTO)throws RuntimeException {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        user.setBalance(user.getBalance() + replenishmentDTO.getValue());
        userRepository.save(user);
        replenishmentRepository.save(Replenishment.builder()
                .sum(replenishmentDTO.getValue())
                .user(user)
                .time(LocalDateTime.now())
                .build());
    }

    @Transactional
    public Page<Replenishment> getAllReplenishments(int page, int size,
                                                    String sortField,
                                                    UserDetails userDetails)throws UserNotFoundException{

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        Pageable pageable = buildPage(sortField, false, page, size);
        return replenishmentRepository.findById(user.getId(), pageable);
    }

    private Pageable buildPage(String sortField, boolean asc, int page, int size) {
        Optional<Sort> sort = asc ? Optional.of(Sort.by(sortField).ascending())
                : Optional.of(Sort.by(sortField).descending());

        return PageRequest.of(page - 1, size, sort.get());
    }

}
