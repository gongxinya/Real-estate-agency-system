package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stacs.estate.cs5031p3code.mapper.PermissionMapper;
import stacs.estate.cs5031p3code.mapper.UserMapper;
import stacs.estate.cs5031p3code.model.dto.SecurityUser;
import stacs.estate.cs5031p3code.model.po.User;

import java.util.Objects;

/**
 * The class for authenticating in Spring Security.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The UserMapper.
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * The PermissionMapper.
     */
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * The method for authenticating the user.
     *
     * @param userEmail The user email.
     * @return Return the details of user.
     * @throws UsernameNotFoundException The UsernameNotFoundException object.
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Generate a query wrapper.
        var queryWrapper = new LambdaQueryWrapper<User>();
        // Generate the query condition.
        queryWrapper.eq(User::getUserEmail, userEmail);
        var user = userMapper.selectOne(queryWrapper);
        // Throw an exception if the user is not queried.
        if (Objects.isNull(user)) {
            throw new RuntimeException("Email or password is error!");
        }
        // Find the permission list.
        var list = permissionMapper.selectPermissionsByUserId(user.getUserId());
        // Encapsulate a SecurityUser object.
        return SecurityUser.builder()
                .user(user)
                .permissions(list)
                .build();
    }
}
