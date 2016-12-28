/**
 * 
 */
package io.jbatista.springauth.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jbatista.springauth.model.Credentials;

/**
 * @author jbatista
 *
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired
	private ObjectMapper objectMapper;
	
    //private TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(String url, AuthenticationManager manager) {
         super(new AntPathRequestMatcher(url));
         setAuthenticationManager(manager);
         //tokenAuthenticationService = new TokenAuthenticationService();
    }

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		Credentials credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
		return getAuthenticationManager().authenticate(token);
	}

}
