package stacs.estate.cs5031p3code.filter;

import io.jsonwebtoken.Claims;
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

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Get the user_key
        var token = request.getHeader("user_key");
        if (!StringUtils.hasText(token)) {
            // pass
            filterChain.doFilter(request, response);
            return;
        }
        // parse user_key
        String userid;
        try {
            var claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("User key is invalid!");
        }
        // get user information in redis
        SecurityUser securityUser = redisCache.getCacheObject(userid);
        if (Objects.isNull(securityUser)) {
            throw new RuntimeException("User is not login!");
        }
        //存入SecurityContextHolder
        var authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
