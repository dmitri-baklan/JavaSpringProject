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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**").permitAll()
                .antMatchers("/registration","/login", "/welcome" ).anonymous()
                .antMatchers("/profile/**", "/periodicals", "/periodicals/{\\d}").fullyAuthenticated()
                .antMatchers("/periodicals/{\\d}/edit").hasAuthority("ADMINISTRATOR")
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/profile", true)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login");
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/home")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry());
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

//    @Bean(name="sessionRegistry")
//    SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

}
