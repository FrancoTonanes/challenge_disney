package com.disney.disney_movie.security;


import com.disney.disney_movie.security.filter.CustomAuthenticationFilter;
import com.disney.disney_movie.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.servlet.context-path}")
    private String API_BASE_PATH;


    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/token/refresh").permitAll()
                    .antMatchers(GET,"/movies/**").permitAll()
                    .antMatchers(GET,"/characters/**").permitAll()
                    .antMatchers(GET,"/genre/**").permitAll()
                    .antMatchers(POST, "/auth/register").permitAll()
                    .antMatchers(POST, "/movies/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(POST, "/characters/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(POST, "/genre/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(PUT, "/movies/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(PUT, "/characters/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(PUT, "/genre/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(PATCH, "/movies/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(PATCH, "/characters/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(DELETE, "/movies/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(DELETE, "/characters/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(DELETE, "/genre/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(POST, "/images/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                    .antMatchers(GET, "/users").hasAnyAuthority("ROLE_MANAGER")
                    .antMatchers(POST, "/users").hasAnyAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
