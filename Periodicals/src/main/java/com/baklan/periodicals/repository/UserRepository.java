package com.baklan.periodicals.repository;

import java.util.Optional;
import java.util.Set;

import com.baklan.periodicals.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baklan.periodicals.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<User> findByRole(Role role, Pageable pageable);

    //Page<User> findAll(Pageable pageable);
//
    Page<User> findByEmail(String email,Pageable pageable);

}
