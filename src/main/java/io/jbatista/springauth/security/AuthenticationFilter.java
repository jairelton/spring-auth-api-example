/**
 * 
 */
package io.jbatista.springauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jbatista.springauth.model.User;
import io.jbatista.springauth.service.UserService;

/**
 * Filter that performs JWT token authentication.
 * 
 * Header: <code>Authentication: Bearer [token]</code>
 * 
 * @author jbatista
 *
 */
public class AuthenticationFilter extends GenericFilterBean {
    private UserService userService;

    public AuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Authentication authentication = this.getAuthentication(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication auth = null;

        String token = request.getHeader("Authorization");

        if (token != null) {
            String[] parts = token.split(" ");

            if (parts.length == 2) {
                try {
                    User user = userService.authenticate(parts[1]);

                    if (user != null) {
                        auth = new UserAuthentication(user);
                    }
                } catch (Exception e) {
                    auth = null;
                }
            }
        }

        return auth;
    }
}
