package stacs.estate.cs5031p3code.service.impl;

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
import java.util.Map;
import java.util.Objects;

/**
 * @author hz65
 * @description 针对表【user(The user table)】的数据库操作Service实现
 * @createDate 2023-03-23 22:22:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String, String> login(User user) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());
        var authentication = this.authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("Login failed!");
        }
        var securityUser = (SecurityUser) authentication.getPrincipal();
        var userId = securityUser.getUser().getUserId().toString();
        var jwt = JwtUtil.createJWT(userId);
        var tokenMap = new HashMap<String, String>();
        tokenMap.put("user_key", jwt);
        redisCache.setCacheObject(userId, securityUser);
        return tokenMap;
    }

    @Override
    public void logout() {
        var authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var securityUser = (SecurityUser) authentication.getPrincipal();
        var userId = securityUser.getUser().getUserId().toString();
        redisCache.deleteObject(userId);
    }
}




