package com.advsoftware.EduFlow.config;
import com.advsoftware.EduFlow.config.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    Role student = Role.STUDENT;
    Role instructor = Role.INSTRUCTOR;
    Role admin = Role.ADMIN;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers( "/error").permitAll()
                        .requestMatchers( "/**").permitAll()
//                        .requestMatchers( "/register").permitAll()
//                        .requestMatchers( "/api/auth/login").permitAll()
//                        .requestMatchers( "/users/logout").permitAll()
//                        .requestMatchers( "/admin/make-user-admin/**").permitAll()
//                        .requestMatchers( "/students").hasRole("")
//                        .requestMatchers( "/enroll").hasRole("STUDENT")
//                        .requestMatchers( "/instructors").hasRole("INSTRUCTOR")
//                        .requestMatchers( "/courses").hasRole("INSTRUCTOR")
//                        .requestMatchers("/api/auth/**").hasAnyRole("STUDENT","INSTRUCTOR","ADMIN")
//                        .requestMatchers("/**").hasAnyRole("STUDENT","INSTRUCTOR","ADMIN")
//                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // Singleton


    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
