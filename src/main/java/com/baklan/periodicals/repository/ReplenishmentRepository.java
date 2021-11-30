package com.baklan.periodicals.repository;

import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.entity.periodicals.Subject;
import com.baklan.periodicals.entity.replenishment.Replenishment;
import com.baklan.periodicals.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {


    @Query("SELECT p FROM Replenishment p WHERE p.user.id = :id")
    Page<Replenishment> findById(@Param("id") Long id, Pageable pageable);
}
