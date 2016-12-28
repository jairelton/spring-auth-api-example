/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=GET, path = "/{id}")
    public User getUser(@PathVariable("id") String id) {
//    	return userDao.findById(id);
    	return null;
    }
    
    @RequestMapping(method=POST)
    public User createUser(@RequestBody @Valid User user) {
        userService.createUser(user);
        
        user.setPassword(null);

        return user;
    }
}
