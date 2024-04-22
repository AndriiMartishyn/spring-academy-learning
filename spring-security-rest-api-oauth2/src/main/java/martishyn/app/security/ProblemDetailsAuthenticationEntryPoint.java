package martishyn.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ProblemDetailsAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
    private final ObjectMapper objectMapper;

    public ProblemDetailsAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        delegate.commence(request, response, authException);
        if (authException.getCause() instanceof JwtValidationException validation){
            ProblemDetail problemDetail = ProblemDetail.forStatus(401);
            problemDetail.setType(URI.create("https://tools.ietf.org/html/rfc6750#section-3.1"));
            problemDetail.setTitle("Invalid token");
            problemDetail.setProperty("errors", validation);
            objectMapper.writeValue(response.getWriter(), problemDetail);
        }
    }
}
