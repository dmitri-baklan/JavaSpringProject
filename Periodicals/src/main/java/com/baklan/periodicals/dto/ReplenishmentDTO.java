package com.baklan.periodicals.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReplenishmentDTO {

    @NotNull(message = "{valid.replenishment.sum.size}")
    @Min(value = 1)
    @Max(value = 1000)
//    @Range(min=1,max=1000, message = "")
    private Long value;

//    private Long user_id;

//    private LocalDateTime time =  LocalDateTime.now();
}
