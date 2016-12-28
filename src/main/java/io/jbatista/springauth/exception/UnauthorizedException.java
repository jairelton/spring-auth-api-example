/**
 * 
 */
package io.jbatista.springauth.exception;

/**
 * @author jbatista
 *
 */
public class UnauthorizedException extends ApplicationException {
	private static final long serialVersionUID = -6454452950081181397L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
