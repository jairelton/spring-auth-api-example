/**
 * 
 */
package io.jbatista.springauth.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jbatista.springauth.dao.UserDao;
import io.jbatista.springauth.exception.ConflictException;
import io.jbatista.springauth.exception.NotFoundException;
import io.jbatista.springauth.exception.UnauthorizedException;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.tokenExpiration}")
    private Long jwtTokenExpiration = Long.MAX_VALUE;

    private Algorithm algorithm;

    public UserServiceImpl(@Value("${jwt.secret}") String jwtSecret)
            throws IllegalArgumentException, UnsupportedEncodingException {
        this.algorithm = Algorithm.HMAC256(jwtSecret);
    }

    @Override
    public User findUser(String id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public User createUser(User user) throws ConflictException {
        User validUser = checkUser(user);

        userDao.create(validUser);

        return validUser;
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ConflictException, NotFoundException {
        User savedUser = userDao.findById(user.getId());

        if (savedUser == null) {
            throw new NotFoundException("User not found.");
        }

        savedUser.setEmail(user.getEmail());
        savedUser.setName(user.getName());
        savedUser.setPassword(user.getPassword());
        savedUser.setPhones(user.getPhones());

        savedUser = checkUser(savedUser);

        userDao.update(savedUser);

        return savedUser;
    }

    private User checkUser(User user) throws ConflictException {
        User conflictingUser = userDao.findByEmail(user.getEmail());

        if (conflictingUser != null && !conflictingUser.getId().equals(user.getId())) {
            throw new ConflictException(String.format("E-mail %s already exists.", user.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String token = createToken(user);
        user.setToken(token);

        user.setLastLogin(new Date());

        return user;
    }

    @Override
    @Transactional
    public User login(String email, String password) {
        User user = userDao.findByEmail(email);

        if (user != null) {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                user = null;
            } else {
                String token = createToken(user);

                user.setToken(token);
                user.setLastLogin(new Date());

                userDao.update(user);
            }
        }

        return user;
    }

    private String createToken(User user) {
        Date expiration = new Date(System.currentTimeMillis() + jwtTokenExpiration);

        return JWT.create().withIssuer(jwtIssuer).withSubject(user.getEmail()).withIssuedAt(new Date())
                .withExpiresAt(expiration).sign(algorithm);
    }

    @Override
    public User authenticate(String token) throws UnauthorizedException {
        try {
            DecodedJWT decodedToken = JWT.require(algorithm).withIssuer(jwtIssuer).build().verify(token);

            User user = userDao.findByEmail(decodedToken.getSubject());

            if (user != null) {
                long lastLogin = user.getLastLogin() == null ? Long.MAX_VALUE
                        : System.currentTimeMillis() - user.getLastLogin().getTime();

                if (lastLogin > jwtTokenExpiration || user.getToken() == null
                        || !user.getToken().equals(decodedToken.getToken())) {
                    user = null;
                }
            }

            if (user == null) {
                throw new UnauthorizedException("Invalid token.");
            }

            return user;
        } catch (UnauthorizedException ue) {
            throw ue;
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token.");
        }
    }

}
