/**
 * 
 */
package io.jbatista.springauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jbatista.springauth.exception.CustomAuthenticationEntryPoint;
import io.jbatista.springauth.service.UserService;

/**
 * Provides security configurations to Spring Security.
 * 
 * @author jbatista
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf()
            .disable()
        .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/users", "/login").permitAll()
            .antMatchers("/403").permitAll()
            .anyRequest().authenticated()
        .and()
            .addFilterBefore(new AuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }
}
