/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jbatista.springauth.model.User;
import io.jbatista.springauth.service.UserService;

/**
 * @author jbatista
 *
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = POST)
    public User login(@RequestBody User user) {
        User loggedUser = userService.authenticate(user.getEmail(), user.getPassword());
        
        if (loggedUser == null) {
            throw new RuntimeException("user not found");
        }
        
        return loggedUser;
    }
}
