/**
 * 
 */
package io.jbatista.springauth.model;

import javax.persistence.Entity;

/**
 * Phone entity.
 * 
 * @author jbatista
 */
@Entity
public class Phone extends BasicModel {
    private static final long serialVersionUID = 5469376705102040630L;

    private String ddd;
    private String number;

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
