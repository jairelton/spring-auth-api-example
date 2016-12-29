/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jbatista.springauth.exception.ConflictException;
import io.jbatista.springauth.exception.NotFoundException;
import io.jbatista.springauth.exception.UnauthorizedException;
import io.jbatista.springauth.model.User;
import io.jbatista.springauth.service.UserService;

/**
 * Controller for /users endpoint.
 * 
 * @author jbatista
 */
@RestController
@RequestMapping(path = "/users", consumes = "application/json", produces = "application/json")
public class UsersController {
    @Autowired
    private UserService userService;

    /**
     * Retrieves the user profile.
     * 
     * @param id
     *            User ID.
     * @param principal
     *            Authenticated user.
     * @return User profile.
     * @throws UnauthorizedException
     *             If the ID doesn't match the authenticated user ID.
     */
    @RequestMapping(method = GET, path = "/{id}")
    public User getUser(@PathVariable("id") String id, @AuthenticationPrincipal User principal)
            throws UnauthorizedException {

        checkPermission(id, principal);

        return userService.findUser(id);
    }

    /**
     * Creates a new user.
     * 
     * @param user
     *            User data.
     * @return Newly created user.
     * @throws ConflictException
     *             If the email is already in use by another user.
     */
    @RequestMapping(method = POST)
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws ConflictException {
        User createdUser = userService.createUser(user);

        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * 
     * @param id
     *            User ID.
     * @param user
     *            User data.
     * @param principal
     *            Authenticated user.
     * @return Newly updated user.
     * @throws ConflictException
     *             If the email is already in user by another user.
     * @throws NotFoundException
     *             If there's no user with the given ID.
     * @throws UnauthorizedException
     *             If the ID doesn't match the authenticated user ID.
     */
    @RequestMapping(method = PUT, path = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody @Valid User user,
            @AuthenticationPrincipal User principal)
            throws ConflictException, NotFoundException, UnauthorizedException {

        checkPermission(id, principal);

        user.setId(id);

        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    private void checkPermission(String id, User principal) throws UnauthorizedException {
        if (principal == null || !id.equals(principal.getId())) {
            throw new UnauthorizedException("Permission denied.");
        }
    }
}
