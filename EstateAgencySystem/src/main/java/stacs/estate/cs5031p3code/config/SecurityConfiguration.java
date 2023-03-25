package stacs.estate.cs5031p3code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stacs.estate.cs5031p3code.filter.JwtAuthenticationTokenFilter;

/**
 * A configuration class for configuring Spring Security.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * The filter for JWT Authentication.
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * The handler for handling with the exception about Authentication.
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * The handler for handling with the exception about Authorization.
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * Override the configure method for using project own configuration.
     *
     * @param http The HttpSecurity object.
     * @throws Exception The Exception object.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure such configuration about authentication.
        http
                // Disable csrf.
                .csrf().disable()
                // Get the SecurityContext without the Session.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // For the login interface, allowing anonymous access.
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/create").anonymous()
                // All requests other than the above require forensic authentication.
                .anyRequest().authenticated();

        // Add the JWT filter when system does authentication.
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // Configuring the exception handler.
        http.exceptionHandling()
                // Configuring the authentication failure handler.
                .authenticationEntryPoint(authenticationEntryPoint)
                // Configuring the authorization failure handler.
                .accessDeniedHandler(accessDeniedHandler);

        // Allow CORS in Spring Security.
        http.cors();
    }

    /**
     * The method for getting the AuthenticationManager object.
     *
     * @return Return the AuthenticationManager object.
     * @throws Exception The Exception object.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * The method for creating BCryptPasswordEncoder.
     *
     * @return return the PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
