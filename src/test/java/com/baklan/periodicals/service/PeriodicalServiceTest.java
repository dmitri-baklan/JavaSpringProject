package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.PeriodicalDTO;
import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.repository.PeriodicalRepository;
import com.baklan.periodicals.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PeriodicalServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PeriodicalRepository periodicalRepository;
    @Autowired
    private UserService userService;
    @MockBean
    private PeriodicalService periodicalService;
    @MockBean
    private Pageable pageable;
    @MockBean
    private Page<Periodical> page;


    @BeforeEach
    void setUp(){

    }

    @Test
    void savePeriodical() {
        PeriodicalDTO periodicalDTO = new PeriodicalDTO();
        Periodical periodical = new Periodical();
        periodicalDTO.setName("Unique");
        periodicalService.savePeriodical(periodicalDTO);

        verify(periodicalService, times(1)).savePeriodical(periodicalDTO);
    }

    @Test
    void getAllPeriodicals() {
    }

    @Test
    void updatePeriodical() {

    }

    @Test
    void changeSubscription() {

    }
}