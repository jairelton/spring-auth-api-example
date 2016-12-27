/**
 * 
 */
package io.jbatista.springauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jbatista.springauth.dao.UserDao;
import io.jbatista.springauth.model.User;
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
    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User authenticate(String email, String password) {
        // TODO Auto-generated method stub
        return null;
    }

}
