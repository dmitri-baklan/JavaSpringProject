package com.baklan.periodicals.config;

import com.baklan.periodicals.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserService userService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/css/**", "/js/**", "/images/**", "/welcome", "/login").permitAll()
//                .antMatchers("/registration").anonymous()
//                .antMatchers("/profile/**").fullyAuthenticated()
//
//                .antMatchers("/products").hasAnyAuthority("CASHIER", "COMMODITY_EXPERT")
//                .antMatchers("/session/{\\d+}/check/overview").hasAnyAuthority("CASHIER", "SENIOR_CASHIER")
//
//                .antMatchers("/session/requests", "/session/{\\d+}/close",
//                        "/sales/{\\d+}/delete", "/shifts/**").hasAuthority("SENIOR_CASHIER")
//
//                .antMatchers("/session/**", "/sales/**").hasAuthority("CASHIER")
//
//                .antMatchers("/products/**").hasAuthority("COMMODITY_EXPERT")
//
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .loginPage("/login")
//                .defaultSuccessUrl("/profile", true)
//                .and()
//                .logout().permitAll()
//                .logoutSuccessUrl("/welcome");

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/welcome", "/login").permitAll()
                .antMatchers("/registration").anonymous()
                .antMatchers("/profile/**").fullyAuthenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")

                .and()

                .logout().permitAll()
                .logoutSuccessUrl("/welcome");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        super.configure(auth);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
