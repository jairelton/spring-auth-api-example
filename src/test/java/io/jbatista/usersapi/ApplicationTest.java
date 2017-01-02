/**
 * 
 */
package io.jbatista.usersapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.jbatista.springauth.Application;
import io.jbatista.springauth.exception.ErrorMessage;
import io.jbatista.springauth.model.Phone;
import io.jbatista.springauth.model.User;
import io.jbatista.springauth.security.Credentials;

/**
 * @author jbatista
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void shouldSignupSuccessfully() {
        User user = getMockUser("user1@jbatista.io");

        ResponseEntity<User> response = this.restTemplate.postForEntity("/users", user, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        User body = response.getBody();

        assertThat(body.getId()).isNotEmpty();
        assertThat(body.getCreated()).isCloseTo(new Date(), 2000);
        assertThat(body.getName()).isEqualTo(user.getName());
        assertThat(body.getEmail()).isEqualTo(user.getEmail());
        assertThat(body.getLastLogin()).isCloseTo(new Date(), 2000);
        assertThat(body.getToken()).isNotEmpty();
        assertThat(body.getPhones()).hasSameSizeAs(user.getPhones());
    }

    @Test
    public void shouldRejectInvalidUser() {
        ResponseEntity<ErrorMessage> response = this.restTemplate.postForEntity("/users", new User(),
                ErrorMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isNotEmpty();
    }

    @Test
    public void shouldRejectDuplicatedEmail() {
        User firstUser = createUser("user2@jbatista.io");
        User secondUser = getMockUser(firstUser.getEmail());

        ResponseEntity<ErrorMessage> response = this.restTemplate.postForEntity("/users", toEntity(secondUser),
                ErrorMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMessage()).isNotEmpty();
    }

    @Test
    public void shouldAcceptLogin() {
        User user = createUser("user3@jbatista.io");
        
        User body = login(user);

        assertThat(body.getEmail()).isEqualTo(user.getEmail());
        assertThat(body.getName()).isEqualTo(user.getName());
        assertThat(body.getLastLogin()).isCloseTo(new Date(), 2000);
        assertThat(body.getToken()).isNotEmpty();
    }

    @Test
    public void shouldRejectInvalidPassword() {
        User user = createUser("user4@jbatista.io");

        Credentials credentials = new Credentials();
        credentials.setEmail(user.getEmail());
        credentials.setPassword(user.getPassword() + "invalid");

        ResponseEntity<Void> response = this.restTemplate.exchange("/login", HttpMethod.POST, toEntity(credentials), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    public void shouldUpdateAnUser() {
        User user = createUser("user5@jbatista.io");
        
        User updatedUser = getMockUser("user5@jbatista.io");
        
        updatedUser.setName("New user name");

        ResponseEntity<User> response = this.restTemplate.exchange("/users/{id}", HttpMethod.PUT,
                toEntity(updatedUser, user.getToken()), User.class, user.getId());
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(updatedUser.getName());
        assertThat(response.getBody().getModified()).isCloseTo(new Date(), 2000);
    }
    
    @Test
    public void shouldDenyUpdateToWrongUser() {
        User firstUser = createUser("user6@jbatista.io");
        User secondUser = createUser("user7@jbatista.io");

        User updatedUser = getMockUser("user7@jbatista.io");

        updatedUser.setName("New user name");

        ResponseEntity<Void> response = this.restTemplate.exchange("/users/{id}", HttpMethod.PUT,
                toEntity(updatedUser, firstUser.getToken()), Void.class, secondUser.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldReturnUserProfile() {
        User user = createUser("user8@jbatista.io");
        
        HttpEntity<?> entity = toEntity(null, user.getToken());

        ResponseEntity<User> response = this.restTemplate.exchange("/users/{id}", HttpMethod.GET, entity, User.class,
                user.getId());

        User body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getId()).isEqualTo(user.getId());
        assertThat(body.getName()).isEqualTo(user.getName());
        assertThat(body.getEmail()).isEqualTo(user.getEmail());
        assertThat(body.getToken()).isEqualTo(user.getToken());
    }

    @Test
    public void shouldDenyAccessToWrongUserProfile() {
        User firstUser = createUser("user9@jbatista.io");
        User secondUser = createUser("user10@jbatista.io");

        HttpEntity<?> entity = toEntity(null, firstUser.getToken());

        ResponseEntity<Void> response = this.restTemplate.exchange("/users/{id}", HttpMethod.GET, entity,
                Void.class, secondUser.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private User getMockUser(String email) {
        User user = new User();
        user.setName("Mock User");
        user.setEmail(email);
        user.setPassword("mockpass");
        Phone phone = new Phone();
        phone.setDdd("99");
        phone.setNumber("0000-0000");
        user.setPhones(Collections.singletonList(phone));

        return user;
    }

    private <T> HttpEntity<T> toEntity(T model) {
        return this.toEntity(model, null);
    }

    private <T> HttpEntity<T> toEntity(T model, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if (token != null) {
            headers.add("Authorization", String.format("Bearer %s", token));
        }

        return new HttpEntity<T>(model, headers);
    }
    
    private User createUser(String email) {
        User user = getMockUser(email);

        ResponseEntity<User> response = this.restTemplate.postForEntity("/users", toEntity(user), User.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        response.getBody().setPassword(user.getPassword());
        
        return response.getBody();
    }
    
    private User login(User user) {
        Credentials credentials = new Credentials();
        credentials.setEmail(user.getEmail());
        credentials.setPassword(user.getPassword());

        ResponseEntity<User> response = this.restTemplate.postForEntity("/login", toEntity(credentials),
                User.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        return response.getBody();
    }
 
}
