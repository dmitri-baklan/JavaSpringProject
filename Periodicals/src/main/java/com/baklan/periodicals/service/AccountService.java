//package com.baklan.periodicals.service;
//
//import com.baklan.periodicals.entity.account.Account;
//import com.baklan.periodicals.entity.user.User;
//import com.baklan.periodicals.repository.AccountRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@AllArgsConstructor
//@Slf4j
//@Service
//public class AccountService {
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    public void createAccount(User user){
//        Account account = Account.builder()
//                .balance(0L)
//                .user(user)
//                .build();
//        accountRepository.save(account);
//    }
//}
