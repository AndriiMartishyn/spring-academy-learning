package martishyn.app.annotation;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression = "authentication.name")
public @interface CurrentOwner {
}
