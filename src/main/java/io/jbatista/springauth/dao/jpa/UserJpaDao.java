/**
 * 
 */
package io.jbatista.springauth.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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

	@Override
	public User findByEmail(String email) {
		User user;

		TypedQuery<User> query = getEntityManager().createQuery("from User u where lower(u.email) = lower(:email)", User.class);
		
		query.setParameter("email", email);
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException nre) {
			user = null;
		}
		
		return user;
	}

}
