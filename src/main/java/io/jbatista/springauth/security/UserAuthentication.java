/**
 * 
 */
package io.jbatista.springauth.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jbatista.springauth.model.User;

/**
 * Authentication that uses an User entity as Principal.
 * 
 * @author jbatista
 *
 */
public class UserAuthentication implements Authentication {
    private static final long serialVersionUID = -5593061574099333568L;

    private User user;
    private boolean authenticated;

    public UserAuthentication(User user) {
        this.user = user;
        this.authenticated = true;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }
}
