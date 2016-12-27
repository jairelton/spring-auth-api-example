/**
 * 
 */
package io.jbatista.springauth.dao;

import java.util.Collection;

/**
 * Provides basic database operations for the given Type and Key.
 * 
 * @author jbatista
 */
public interface DataAccessObject<T, K> {
    public Collection<T> findAll();
    public T findById(K id);
    public void update(T entity);
    public void create(T entity);
    public void delete(K id);
}
