/**
 * 
 */
package io.jbatista.springauth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jbatista.springauth.exception.ErrorMessage;
import io.jbatista.springauth.exception.UnauthorizedException;
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
            throw new UnauthorizedException("Invalid username/password");
        }
        
        return loggedUser;
    }
    
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ErrorMessage handleException(HttpServletResponse response, UnauthorizedException ue) throws IOException {
		return new ErrorMessage(ue.getMessage());
	}
}
