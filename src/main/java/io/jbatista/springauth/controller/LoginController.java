/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jbatista.springauth.exception.UnauthorizedException;
import io.jbatista.springauth.model.User;
import io.jbatista.springauth.security.Credentials;
import io.jbatista.springauth.service.UserService;

/**
 * Controller to /login endpoint.
 * 
 * @author jbatista
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * Processes a login request.
     * 
     * @param user
     *            User credentials.
     * @return Logged in user.
     * @throws UnauthorizedException
     *             Invalid credentials.
     */
    @RequestMapping(path = "/login", method = POST, consumes = "application/json", produces = "application/json")
    public User login(@RequestBody Credentials credentials) throws UnauthorizedException {
        User loggedUser = null;

        if (credentials != null && credentials.getEmail() != null && credentials.getPassword() != null) {
            loggedUser = userService.login(credentials.getEmail(), credentials.getPassword());
        }

        if (loggedUser == null) {
            throw new UnauthorizedException("Invalid username/password");
        }

        return loggedUser;
    }
}
