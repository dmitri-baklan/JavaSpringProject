package com.baklan.periodicals.entity.user;

import com.baklan.periodicals.entity.periodicals.Periodical;
import lombok.*;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(
            name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"email"})
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "users_periodicals",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "periodical_id"))
    List<Periodical> periodicals;

}
