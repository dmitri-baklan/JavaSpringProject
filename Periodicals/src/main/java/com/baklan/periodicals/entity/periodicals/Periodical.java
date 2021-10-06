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
    @Generated
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String price;

    @Column
    private Long subscribers;

}
