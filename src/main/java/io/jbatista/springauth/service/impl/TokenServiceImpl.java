/**
 * 
 */
package io.jbatista.springauth.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jbatista.springauth.service.TokenService;

/**
 * @author jair
 *
 */
@Service
public class TokenServiceImpl implements TokenService {
	@Value("${jwt.issuer}")
	private String jwtIssuer;
	
	private Algorithm algorithm;
	
	public TokenServiceImpl(@Value("${jwt.secret}") String jwtSecret) throws IllegalArgumentException, UnsupportedEncodingException {
		this.algorithm = Algorithm.HMAC256(jwtSecret);
	}

	@Override
	public String createToken(String email) {
		return JWT.create()
				.withIssuer(jwtIssuer)
				.withSubject(email)
		        .sign(algorithm);
	}

	@Override
	public DecodedJWT decodeToken(String token) {
		return JWT.require(algorithm)
				.withIssuer(jwtIssuer)
				.build()
				.verify(token);
	}

}
