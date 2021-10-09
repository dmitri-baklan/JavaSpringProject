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
public class PeriodicalsDTO {

    @NotBlank(message = "{}")
    @Size(min=2, max=20, message = "{}")
    @Pattern(regexp = Regex.REGEX_NAME, message = "{}")
    private String name;

    private Subject subject;

    @Positive
    @NotNull(message = "{}")
    @Range(min=10,max=1000, message = "{}")
    private Long price;

    private Long subscribers;
}
