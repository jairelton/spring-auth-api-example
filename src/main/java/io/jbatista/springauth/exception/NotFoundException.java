/**
 * 
 */
package io.jbatista.springauth.exception;

/**
 * Thrown to indicate that a resource does not exists.
 * 
 * @author jbatista
 *
 */
public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1434705881917682242L;

    public NotFoundException(String message) {
        super(message);
    }
}
