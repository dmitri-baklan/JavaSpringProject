package com.baklan.periodicals.dto;

import lombok.*;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {

    @NotBlank(message = "{valid.user.name.blank}")
    @Size(min=2, max=20, message = "{valid.user.name.size}")
    @Pattern(regexp = Regex.REGEX_NAME, message = "{valid.user.name.regex}")
    private String name;

    @NotBlank(message = "{valid.user.name.blank}")
    @Size(min=2, max=20, message = "{valid.user.name.size}")
    @Pattern(regexp = Regex.REGEX_NAME, message = "{valid.user.name.regex}")
    private String surname;

    @NotBlank(message = "{valid.user.email.blank}")
    @Size(max=20, message = "{valid.user.email.size}")
    @Email(message = "{valid.user.email.regex}")
    private String email;

    @NotBlank(message = "{valid.user.password.blank}")
    @Size(min=6, max=20, message = "{valid.user.password.size}")
    private String password;

    private String role;
}
