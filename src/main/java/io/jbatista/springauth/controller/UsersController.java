/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jbatista.springauth.dao.UserDao;
import io.jbatista.springauth.model.User;

/**
 * @author jbatista
 *
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method=GET)
    public Collection<User> list() {
        return userDao.findAll();
    }
    
    @RequestMapping(method=POST)
    public User createUser(@RequestBody @Valid User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userDao.create(user);

        return user;
    }
}
