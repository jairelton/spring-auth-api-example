/**
 * 
 */
package io.jbatista.springauth.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jbatista.springauth.dao.UserDao;
import io.jbatista.springauth.model.User;
import io.jbatista.springauth.service.TokenService;
import io.jbatista.springauth.service.UserService;

/**
 * Default UserService implementation.
 * 
 * @author jbatista
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private TokenService tokenService;

    @Override
    public User createUser(User user) {
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(encodedPassword);

    	userDao.create(user);

    	return user;
    }

    @Override
    @Transactional
    public User authenticate(String email, String password) {
    	User user = userDao.findByEmail(email);
    	
    	if (user != null) {
    		if (!passwordEncoder.matches(password, user.getPassword())) {
    			user = null;
    		} else {
    			String token = tokenService.createToken(email);

    			user.setToken(token);
    			user.setLastLogin(new Date());

    			userDao.update(user);
    		}
    	}

        return user;
    }

}
