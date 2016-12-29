/**
 * 
 */
package io.jbatista.springauth.service;

import io.jbatista.springauth.exception.ConflictException;
import io.jbatista.springauth.exception.NotFoundException;
import io.jbatista.springauth.exception.UnauthorizedException;
import io.jbatista.springauth.model.User;

/**
 * Provides operations for managing users.
 * 
 * @author jbatista
 */
public interface UserService {
    /**
     * Retrieves an user by its ID.
     * 
     * @param id
     *            user id;
     * @return Newly saved user.
     */
    public User findUser(String id);

    /**
     * Inserts a new user.
     * 
     * @param user
     *            User to be saved.
     * @return Newly saved user.
     * @throws ConflictException
     *             If the email is already in use by another user.
     */
    public User createUser(User user) throws ConflictException;

    /**
     * Updates an existing user.
     * 
     * @param user
     *            User to be updated.
     * @return Newly updated user.
     * @throws ConflictException
     *             If the email is already in use by another user.
     * @throws NotFoundException
     *             If the user doesn't exists.
     */
    public User updateUser(User user) throws ConflictException, NotFoundException;

    /**
     * Check token validity.
     * 
     * @param token
     *            JWT token.
     * @return Logged in user.
     * @throws UnauthorizedException
     *             If the token is not valid.
     */
    public User authenticate(String token) throws UnauthorizedException;

    /**
     * Validates the given login information and retrieves the logged in user.
     * 
     * @param email
     *            User email.
     * @param password
     *            User password (plain text).
     * @return Logged in user or null if the provided information is invalid.
     */
    public User login(String email, String password);
}
