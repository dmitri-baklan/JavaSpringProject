package com.baklan.periodicals.entity.periodicals;


import com.baklan.periodicals.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "periodicals",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
        }
)
public class Periodical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Column
    private Long price;

    @Column
    private Long subscribers;

    @ManyToMany(mappedBy = "periodicals")
    Set<User> users;
}
