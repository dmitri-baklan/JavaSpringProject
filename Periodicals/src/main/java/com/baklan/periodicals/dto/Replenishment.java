package com.baklan.periodicals.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Replenishment {

    @NotNull(message = "{valid.replenishment.sum.blank}")
    @Range(min=10,max=1000, message = "{valid.replenishment.sum.size}")
    private Long sum;

    private Long account_id;
}
