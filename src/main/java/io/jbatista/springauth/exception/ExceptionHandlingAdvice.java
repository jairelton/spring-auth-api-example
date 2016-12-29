/**
 * 
 */
package io.jbatista.springauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handling controller advice to configure error handling to all
 * controllers.
 * 
 * @author jbatista
 *
 */
@RestControllerAdvice
public class ExceptionHandlingAdvice {
    /**
     * Handles UnauthorizedException.
     * 
     * @param ue
     *            The exception.
     * @return Error message.
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorMessage handleUnauthorizedException(UnauthorizedException ue) {
        return new ErrorMessage(ue.getMessage());
    }

    /**
     * Handles ConflictException.
     * 
     * @param ue
     *            The exception.
     * @return Error message.
     */
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ErrorMessage handleConflictException(ConflictException ce) {
        return new ErrorMessage(ce.getMessage());
    }

    /**
     * Handles NotFoundException.
     * 
     * @param ue
     *            The exception.
     * @return Error message.
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFoundException(NotFoundException nfe) {
        return new ErrorMessage(nfe.getMessage());
    }
}
