package com.baklan.periodicals.entity.periodicals;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String description;

    @Column
    private Long price;

    @Column
    private Long subscribers;

}
