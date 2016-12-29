/**
 * 
 */
package io.jbatista.springauth.exception;

/**
 * Thrown to indicate that the request was denied due to invalid credentials or
 * lack of permissions.
 * 
 * @author jbatista
 */
public class UnauthorizedException extends Exception {
	private static final long serialVersionUID = -6454452950081181397L;

	public UnauthorizedException(String message) {
		super(message);
	}
}
