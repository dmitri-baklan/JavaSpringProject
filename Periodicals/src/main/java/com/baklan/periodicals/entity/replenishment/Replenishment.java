package com.baklan.periodicals.entity.replenishment;

import com.baklan.periodicals.entity.account.Account;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "replenishments"
)
public class Replenishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sum;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
