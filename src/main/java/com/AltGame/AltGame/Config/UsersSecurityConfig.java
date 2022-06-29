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
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/signup", "/swagger-ui.html/**","/refresh-token","/api/products/index","/api/products","/api/bids/**").permitAll();
//        http.authorizeRequests().antMatchers("/api/bids/**").hasAnyAuthority("buyer");
//        http.authorizeRequests().antMatchers("/api/products/**").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers( "/api/**").hasAnyAuthority("buyer","seller");
        http.authorizeRequests().anyRequest().authenticated();

        RefreshToken refreshToken = new RefreshToken(authenticationManagerBean()); //customize url login
        refreshToken.setFilterProcessesUrl("/api/login"); //customize url login
        http.addFilter(refreshToken);//customize url login


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
