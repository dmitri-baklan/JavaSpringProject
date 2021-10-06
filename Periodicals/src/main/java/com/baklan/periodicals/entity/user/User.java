package com.baklan.periodicals.entity.user;

import lombok.*;


import javax.persistence.*;
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
    @Generated
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;


}
