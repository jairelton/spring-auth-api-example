/**
 * 
 */
package io.jbatista.springauth.dao;

import java.util.Collection;

/**
 * Provides basic database operations for the given Type and Key.
 * 
 * @author jbatista
 *
 * @param <T>
 *            Entity type.
 * @param <K>
 *            Key type.
 */
public interface DataAccessObject<T, K> {
    /**
     * Retrieves all entities.
     * 
     * @return List of existing entities.
     */
    public Collection<T> findAll();

    /**
     * Retrieves an entity by its key.
     * 
     * @param key
     *            The entity key.
     * @return Entity matching the given key, or null if not found.
     */
    public T findById(K key);

    /**
     * Updates an existing entity.
     * 
     * @param entity
     *            The entity to be updated.
     */
    public void update(T entity);

    /**
     * Persists a new entity.
     * 
     * @param entity
     *            The entity to be persisted.
     */
    public void create(T entity);

    /**
     * Removes an existing entity.
     * 
     * @param key
     *            The entity key.
     */
    public void delete(K id);
}
