package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import stacs.estate.cs5031p3code.model.dto.SecurityUser;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.service.UserService;
import stacs.estate.cs5031p3code.mapper.UserMapper;
import org.springframework.stereotype.Service;
import stacs.estate.cs5031p3code.utils.JwtUtil;
import stacs.estate.cs5031p3code.utils.RedisCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The implement of service for database operations on the user table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * The manager of Authentication.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * The cache of redis.
     */
    @Autowired
    private RedisCache redisCache;

    /**
     * The user mapper.
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * The method for login.
     *
     * @param user The user information.
     * @return Return the user_key.
     */
    @Override
    public Map<String, String> login(User user) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());
        // Get the authentication object.
        var authentication = this.authenticationManager.authenticate(authenticationToken);
        // Login failed.
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("Login failed!");
        }
        // Get the authentication user.
        var securityUser = (SecurityUser) authentication.getPrincipal();
        // Get user id.
        var userId = securityUser.getUser().getUserId().toString();
        // Generate the JWT.
        var jwt = JwtUtil.createJWT(userId);
        var tokenMap = new HashMap<String, String>();
        tokenMap.put("user_key", jwt);
        // Store the JWT to redis.
        redisCache.setCacheObject(userId, securityUser);
        return tokenMap;
    }

    /**
     * The method for logout.
     */
    @Override
    public void logout() {
        // Get the authentication information.
        var authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // Get the current user.
        var securityUser = (SecurityUser) authentication.getPrincipal();
        // Get user id.
        var userId = securityUser.getUser().getUserId().toString();
        // Delete the user id from redis.
        redisCache.deleteObject(userId);
    }

    /**
     * The method for getting user list.
     *
     * @return Return the user list.
     */
    @Override
    public List<User> getUserList() {
        return userMapper.selectList(null);
    }
}




