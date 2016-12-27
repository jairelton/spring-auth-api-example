/**
 * 
 */
package io.jbatista.springauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application starting point.
 * 
 * @author jbatista
 */
@SpringBootApplication
//@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
