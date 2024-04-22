package martishyn.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain appSecurity(HttpSecurity httpSecurity,
                                    ProblemDetailsAuthenticationEntryPoint entryPoint) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) ->
                        oauth2.authenticationEntryPoint(entryPoint)
                                .jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
