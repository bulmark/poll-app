package com.example.pollprojectmain.config;

import com.example.pollprojectmain.config.jwt.AuthEntryPointJwt;
import com.example.pollprojectmain.config.jwt.AuthTokenFilter;
import com.example.pollprojectmain.service.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private  UserDetailsService userDetailsService;

    @Autowired
    private  AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private  AuthTokenFilter authenticationJwtTokenFilter;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/signin").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
                        .disable())
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public DefaultWebSecurityExpressionHandler expressionHandler() {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setDefaultRolePrefix(""); // Устанавливаем пустой префикс ролей
//        return expressionHandler;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
