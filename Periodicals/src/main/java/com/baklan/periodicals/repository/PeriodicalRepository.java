package com.baklan.periodicals.repository;

import com.baklan.periodicals.entity.periodicals.Periodical;

import com.baklan.periodicals.entity.periodicals.Subject;
import com.baklan.periodicals.entity.user.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {

    Optional<Periodical> findById(Long id);

    Page<Periodical> findAll(Pageable pageable);

    Page<Periodical> findByName(String searchQuery, Pageable pageable);

    @Query("SELECT p FROM Periodical p WHERE p.subject = :subj")
    Page<Periodical> findBySubject(@Param("subj") Subject subj, Pageable pageable);

}
