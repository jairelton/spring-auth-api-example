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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author jbatista
 *
 */
public class TokenValidatorFilter extends GenericFilterBean {	
	private JWTVerifier tokenVerifier;
	
	public TokenValidatorFilter(String jwtSecret, String jwtIssuer) throws Exception {
		tokenVerifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withIssuer(jwtIssuer).build();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = getAuthentication((HttpServletRequest) request);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	private Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			DecodedJWT jwt = tokenVerifier.verify(token);
		}

		return null;
	}
}
