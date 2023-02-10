package com.pri.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pri.auth.payload.UserService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
	private UserService request;
	
	@Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		      http
		        .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/public").permitAll()
                .requestMatchers("/user").authenticated()
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		      http.authenticationProvider(authenticationProvider());
		       
          return http.build();
    }

	@Bean
    public AuthenticationProvider authenticationProvider() {
    	 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(request);
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
		
		@Bean
	    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
			
	        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.userDetailsService(request).passwordEncoder(passwordEncoder());
	        return authenticationManagerBuilder.build();
    }
}
	
