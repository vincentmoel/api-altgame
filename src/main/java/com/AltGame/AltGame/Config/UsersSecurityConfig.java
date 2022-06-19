package com.AltGame.AltGame.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UsersSecurityConfig extends WebSecurityConfigurerAdapter {
    public final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/signup", "/swagger-ui.html/**","/refresh-token","/api/products/").permitAll();
        http.authorizeRequests().antMatchers("/api/users/signup/seller","/api/users/update/buyer").hasAnyAuthority("buyer");
        http.authorizeRequests().antMatchers("/api/users/update/seller").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers("/api/users/id/").hasAnyAuthority("buyer","seller");


        http.authorizeRequests().anyRequest().authenticated();

        http.addFilterBefore(new CustomeAuthorFillter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new RefreshToken(authenticationManagerBean()));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
