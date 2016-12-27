/**
 * 
 */
package io.jbatista.springauth.dao.jpa;

import org.springframework.stereotype.Service;

import io.jbatista.springauth.dao.UserDao;
import io.jbatista.springauth.model.User;

/**
 * JPA implementation data access operations for User entity.
 * 
 * @author jbatista
 */
@Service
public class UserJpaDao extends GenericJpaDao<User, String> implements UserDao {

}
