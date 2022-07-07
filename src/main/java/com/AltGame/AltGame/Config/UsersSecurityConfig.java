package com.AltGame.AltGame.Config;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), new ResponseDto("200","Succes Logout"));
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().requiresChannel().anyRequest().requiresSecure();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/signup", "/swagger-ui.html/**","/refresh-token","/api/products/index","/api/products","/api/products/show/**","/api/categories/index/**","/api/bids/**").permitAll();
//        http.authorizeRequests().antMatchers("/api/bids/**").hasAnyAuthority("buyer");
//        http.authorizeRequests().antMatchers("/api/products/**").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers( "/api/**").hasAnyAuthority("buyer","seller");
        http.authorizeRequests().anyRequest().authenticated();
        RefreshToken refreshToken = new RefreshToken(authenticationManagerBean()); //customize url login
        refreshToken.setFilterProcessesUrl("/api/login"); //customize url login
        http.addFilter(refreshToken);//customize url login
        http.addFilterBefore(new CustomeAuthorFillter(), UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutUrl("/api/logout").logoutSuccessHandler(logoutSuccessHandler()).deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod(configuration.ALL);
        configuration.addAllowedHeader(configuration.ALL);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
