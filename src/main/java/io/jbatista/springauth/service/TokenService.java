/**
 * 
 */
package io.jbatista.springauth.service;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author jair
 *
 */
public interface TokenService {
	public String createToken(String email);
	public DecodedJWT decodeToken(String token);
}
