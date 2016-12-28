/**
 * 
 */
package io.jbatista.springauth.exception;

import java.io.Serializable;

/**
 * @author jbatista
 *
 */
public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = -1810051955871326516L;
	private String message;
	
	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
