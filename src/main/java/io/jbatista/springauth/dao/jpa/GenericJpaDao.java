/**
 * 
 */
package io.jbatista.springauth.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import io.jbatista.springauth.dao.DataAccessObject;

/**
 * Provides basic data access operations with JPA.
 * 
 * @author jbatista
 *
 */
public class GenericJpaDao<T, K> implements DataAccessObject<T, K> {
    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> persistentClass;
    
    @SuppressWarnings("unchecked")
    public GenericJpaDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    protected Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    /**
     * Find all entities of the specified type in the database.
     * 
     * @return list of entities.
     */
    @Override
    public Collection<T> findAll() {
        CriteriaQuery<T> criteria = getEntityManager().getCriteriaBuilder().createQuery(getPersistentClass());
        
        criteria.from(getPersistentClass());

        return getEntityManager().createQuery(criteria).getResultList();
    }

    /**
     * Find an entity by its ID.
     * 
     * @param id entity's ID.
     * @return Entity, or null if not found.
     */
    @Override
    public T findById(K id) {
        return getEntityManager().find(getPersistentClass(), id);
    }

    @Override
    @Transactional
    public void update(T entity) {
        getEntityManager().persist(entity);        
    }

    @Override
    @Transactional
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    @Transactional
    public void delete(K id) {
        T entity = findById(id);
        getEntityManager().remove(entity);        
    }
}
