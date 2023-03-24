package stacs.estate.cs5031p3code.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A configuration class for handling with the problem of CORS (Cross-origin resource sharing).
 *
 * @author 220032952
 * @version 0.0.1
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * Override the method about CORS in SpringBoot.
     *
     * @param registry The registry.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Setting the path to allow CORS.
        registry.addMapping("/**")
                // Setting up domain names to allow CORS.
                .allowedOriginPatterns("*")
                // Whether to allow cookies.
                .allowCredentials(true)
                // Set the allowed request methods.
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // Set the allowed header attribute.
                .allowedHeaders("*")
                // Cross domain allowable time
                .maxAge(3600);
    }
}