package com.baklan.periodicals.repository;

import com.baklan.periodicals.entity.replenishment.Replenishment;
import com.baklan.periodicals.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {

}
