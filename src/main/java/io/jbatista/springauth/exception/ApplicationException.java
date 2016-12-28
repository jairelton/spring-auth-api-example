/**
 * 
 */
package io.jbatista.springauth.exception;

/**
 * @author jbatista
 *
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = -8694978934228229535L;

	public ApplicationException(String message) {
		super(message);
	}
}
