/**
 * 
 */
package io.jbatista.springauth.model;

import java.io.Serializable;

/**
 * @author jbatista
 *
 */
public class Credentials implements Serializable {
	private static final long serialVersionUID = -7881471794286587801L;
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
