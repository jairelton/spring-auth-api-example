/**
 * 
 */
package io.jbatista.springauth.service;

import io.jbatista.springauth.model.User;

/**
 * Provides operations for managing users.
 * 
 * @author jbatista
 */
public interface UserService {
    /**
     * Inserts a new user.
     * 
     * @param user User to be saved.
     * @return Newly saved user.
     */
    public User createUser(User user);

    /**
     * Validates the given login information and retrieves the logged in user.
     * 
     * @param email User e-mail.
     * @param password User password (plain text).
     * @return Logged in user or null if the provided information is invalid.
     */
    public User authenticate(String email, String password);
}
