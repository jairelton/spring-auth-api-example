/**
 * 
 */
package io.jbatista.springauth.exception;

/**
 * Thrown to indicate that a data conflict has occurred.
 * 
 * @author jbatista
 * 
 */
public class ConflictException extends Exception {
    private static final long serialVersionUID = 3617371661244137050L;

    public ConflictException(String message) {
        super(message);
    }
}
