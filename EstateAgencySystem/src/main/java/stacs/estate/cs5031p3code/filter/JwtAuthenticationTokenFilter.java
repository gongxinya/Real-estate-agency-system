package stacs.estate.cs5031p3code.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import stacs.estate.cs5031p3code.model.dto.SecurityUser;
import stacs.estate.cs5031p3code.utils.JwtUtil;
import stacs.estate.cs5031p3code.utils.RedisCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * A filter for handling with JWT.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * Getting the redis cache.
     */
    @Autowired
    private RedisCache redisCache;

    /**
     * Override the method for implementing JWT handling logic.
     *
     * @param request     Http request.
     * @param response    Http response.
     * @param filterChain The filter chain.
     * @throws ServletException The ServletException object.
     * @throws IOException      The IOException object.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Get the user_key in request header.
        var token = request.getHeader("user_key");
        // If the request does not have user_key.
        if (!StringUtils.hasText(token)) {
            // Release the filter.
            filterChain.doFilter(request, response);
            return;
        }
        // If the request has user_key, parsing user_key.
        String userId;
        try {
            var claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get user information in redis by userId.
        SecurityUser securityUser = redisCache.getCacheObject(userId);
        if (Objects.isNull(securityUser)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Verify token successfully, store into SecurityContextHolder.
        var authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Release the filter.
        filterChain.doFilter(request, response);
    }
}
