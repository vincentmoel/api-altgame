package com.AltGame.AltGame.Config;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class UsersSecurityConfig extends WebSecurityConfigurerAdapter {
    public final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public Cloudinary getAccount(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "alt-game",
                "api_key", "418412152646768",
                "api_secret", "gy-7J6VEp7u5JWG6FOem12YDUC8"));
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
        http.authorizeRequests().antMatchers("/api/login/**","/auth/login").permitAll();
        http.authorizeRequests().antMatchers("/api/signup", "/swagger-ui.html/**","/refresh-token","/api/products/index","/api/products**","/api/products/show/**","/api/categories/index/**","/api/products/seller/**","/api/categories/index").permitAll();
        http.authorizeRequests().antMatchers("/api/products/**","/api/products/update/**","/api/products/destroy/**","/api/products/my-products","/api/bids/accept-bid-buyer/**","/api/bids/all-bids-product/**").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers( "/api/users/update","/api/bids/store","/api/notifications/**","/api/users/register-seller","/api/users/upload-image","/api/notifications/update-status-to-read/**","/api/notifications/show-user-notifications","/api/invoices/show/**","/api/invoices/pay/**","/api/invoices/index","/api/wishlist/destroy","/api/wishlists/store","/api/is-product-in-wishlist/**","/api/wishlists/index","/api/bids/destroy/**","/api/bids/update/**","/api/bids/show/**","/api/bids/index").hasAnyAuthority("buyer","seller");
        http.authorizeRequests().anyRequest().authenticated();
        RefreshToken refreshToken = new RefreshToken(authenticationManagerBean(), getApplicationContext()); //customize url login
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
