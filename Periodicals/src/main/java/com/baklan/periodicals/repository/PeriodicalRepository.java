package com.baklan.periodicals.repository;

import com.baklan.periodicals.entity.periodicals.Periodical;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {

}
