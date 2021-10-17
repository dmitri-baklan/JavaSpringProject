package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.ReplenishmentDTO;
import com.baklan.periodicals.entity.replenishment.Replenishment;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.repository.ReplenishmentRepository;
import com.baklan.periodicals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//import javax.transaction.Transactional;

@Service
public class ReplenishmentService {

    @Autowired
    private ReplenishmentRepository replenishmentRepository;
//
    @Autowired
    private UserRepository userRepository;

// add exception
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

}
