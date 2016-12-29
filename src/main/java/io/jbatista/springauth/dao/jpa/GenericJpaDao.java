/**
 * 
 */
package io.jbatista.springauth.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import io.jbatista.springauth.dao.DataAccessObject;

/**
 * JPA implementation of DataAccessObject.
 * 
 * @see DataAccessObject
 * 
 * @author jbatista
 *
 * @param <T>
 *            Entity type.
 * @param <K>
 *            Key type.
 *
 */
public class GenericJpaDao<T, K> implements DataAccessObject<T, K> {
    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericJpaDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    @Override
    public Collection<T> findAll() {
        CriteriaQuery<T> criteria = getEntityManager().getCriteriaBuilder().createQuery(getPersistentClass());

        criteria.from(getPersistentClass());

        return getEntityManager().createQuery(criteria).getResultList();
    }

    @Override
    public T findById(K key) {
        return getEntityManager().find(getPersistentClass(), key);
    }

    @Override
    public void update(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void delete(K id) {
        T entity = findById(id);
        getEntityManager().remove(entity);
    }
}
