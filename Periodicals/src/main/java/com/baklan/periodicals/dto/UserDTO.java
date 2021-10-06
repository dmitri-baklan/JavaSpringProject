package com.baklan.periodicals.dto;

import com.baklan.periodicals.dto.UserRegex;
import com.baklan.periodicals.entity.user.Role;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {


    private Long id;

    @NotBlank(message = "{valid.user.name.blank}")
    @Size(min=2, max=20, message = "{valid.user.name.size}")
    @Pattern(regexp = UserRegex.REGEX_NAME, message = "{valid.user.name.regex}")
    private String firstname;

    @NotBlank(message = "{valid.user.name.blank}")
    @Size(min=2, max=20, message = "{valid.user.name.size}")
    @Pattern(regexp = UserRegex.REGEX_NAME, message = "{valid.user.name.regex}")
    private String secondname;

    @NotBlank(message = "{valid.user.email.blank}")
    @Size(max=20, message = "{valid.user.email.size}")
    @Email(message = "{valid.user.email.regex}")    //regexp = UserRegex.REGEX_EMAIL,
    private String email;


    @NotBlank()             //message = "{valid.user.password.blank}"
    @Size(min=6, max=20)    //, message = "{valid.user.password.size}"
    private String password;

//    @Positive
    @NotNull(message = "{valid.user.account.null}")
    @Range(min=10,max=1000, message = "{valid.user.account.size}")
    private Long account;

    private Role role;

}
