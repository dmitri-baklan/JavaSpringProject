package com.baklan.periodicals.dto;

import com.baklan.periodicals.entity.periodicals.Subject;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PeriodicalDTO {

    @NotBlank(message = "{valid.periodical.blank}")
    @Size(min=2, max=20, message = "{valid.periodical.size}")
//    @Pattern(regexp = Regex.REGEX_NAME, message = "{valid.periodical.name.regex}")
    private String name;

    private Subject subject;


    @NotNull(message = "{valid.periodical.price}")
    @Min(value = 1)
    @Max(value = 1000)
    private Long price;


    private Long subscribers=0L;
}
