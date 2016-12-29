/**
 * 
 */
package io.jbatista.springauth.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * Basic model.
 * 
 * @author jbatista
 */
@MappedSuperclass
public abstract class BasicModel implements Serializable {
    private static final long serialVersionUID = 3120147111117554332L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean eq = false;

        if (obj instanceof Phone) {
            Phone other = (Phone) obj;
            
            if (getId() == null) {
                eq = other.getId() == null;
            } else {
                eq = getId().equals(other.getId());
            }
        }

        return eq;
    }
    
    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}
