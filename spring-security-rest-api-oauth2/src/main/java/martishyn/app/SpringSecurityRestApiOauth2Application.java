package martishyn.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class SpringSecurityRestApiOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRestApiOauth2Application.class, args);
    }

}
