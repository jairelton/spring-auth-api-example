/**
 * 
 */
package io.jbatista.springauth.dao;

import io.jbatista.springauth.model.User;

/**
 * Provides database operations for the User entity.
 * 
 * @author jbatista
 */
public interface UserDao extends DataAccessObject<User, String> {
    /**
     * Finds an user by its email.
     * 
     * @param email
     *            User email.
     * @return The user with the given email, or null if not found.
     */
    public User findByEmail(String email);
}
