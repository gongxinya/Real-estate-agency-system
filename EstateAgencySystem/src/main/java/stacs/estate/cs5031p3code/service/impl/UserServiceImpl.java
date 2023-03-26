package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.FlatMapper;
import stacs.estate.cs5031p3code.mapper.RoleMapper;
import stacs.estate.cs5031p3code.mapper.UserRoleMapper;
import stacs.estate.cs5031p3code.model.dto.SecurityUser;
import stacs.estate.cs5031p3code.model.po.Flat;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.model.po.UserRole;
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
     * The role mapper.
     */
    @Autowired
    private RoleMapper roleMapper;

    /**
     * The user role mapper.
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * The flat mapper.
     */
    @Autowired
    private FlatMapper flatMapper;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        // Get user id string.
        var userId = this.getCurrentAuthenticationInformation().toString();
        // Delete the user id from redis.
        redisCache.deleteObject(userId);
    }

    /**
     * The method for register.
     */
    @Override
    public void register(User user) throws EstateException {
        // User properties are null.
        if (Objects.isNull(user)
                || !StringUtils.hasText(user.getUserEmail())
                || !StringUtils.hasText(user.getUserName())
                || !StringUtils.hasText(user.getUserPassword())) {
            throw new EstateException("User email, password and name cannot be empty!");
        }

        // Keep the uniqueness of user email.
        this.checkUserEmail(user);

        // Insert the new user.
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        var result = userMapper.insert(user);
        if (result < 1) {
            throw new EstateException("Register user failed!");
        }

        // Get the Guest role id.
        var roleQuery = new LambdaQueryWrapper<Role>();
        roleQuery.eq(Role::getRoleName, "Guest");
        var role = roleMapper.selectOne(roleQuery);
        var roleId = role.getRoleId();

        // Assign a role to the new user.
        var userRole = UserRole.builder()
                .userId(user.getUserId())
                .roleId(roleId)
                .build();
        result = userRoleMapper.insert(userRole);
        if (result < 1) {
            throw new EstateException("Assigning role to user is failed!");
        }
    }

    /**
     * The method for deleting user by userId.
     *
     * @param userId The user id object.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void deleteUserById(Long userId) throws EstateException {
        // If user id is null
        if (Objects.isNull(userId)) {
            throw new EstateException("User id cannot be empty!");
        }

        // Get the user by userId.
        this.selectUserByUserId(userId);

        // If user has flat, doesn't delete user.
        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getUserId, userId);
        var count = flatMapper.selectCount(flatQueryWrapper);
        if (count > 0) {
            throw new EstateException("User has flats in there, cannot remove!");
        }

        // Delete user in user table.
        var userQueryWrapper = new LambdaQueryWrapper<User>();
        userQueryWrapper.eq(User::getUserId, userId);
        var result = userMapper.delete(userQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete user is failed!");
        }

        // Delete the relationship between user and role.
        var userRoleQueryWrapper = new LambdaQueryWrapper<UserRole>();
        userRoleQueryWrapper.eq(UserRole::getUserId, userId);
        result = userRoleMapper.delete(userRoleQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete the relationship between user and role is failed!");
        }
    }

    /**
     * The method for updating user.
     *
     * @param user The user.
     * @throws EstateException The EstateException user.
     */
    @Override
    public void updateUser(User user) throws EstateException {
        // If user is null.
        if (Objects.isNull(user)) {
            throw new EstateException("User is empty!");
        }

        // Get user id.
        var userId = this.getCurrentAuthenticationInformation();
        user.setUserId(userId);

        // Update user by user id.
        this.updateUserByUserId(userId, user);
    }

    /**
     * The method for updating user by user id.
     *
     * @param userId The user id.
     * @param user   The updated user.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void updateUserByUserId(Long userId, User user) throws EstateException {
        // If the user id is null or user is null
        if (Objects.isNull(userId) || Objects.isNull(user)) {
            throw new EstateException("User or user id is empty!");
        }

        // Keep the uniqueness of user email.
        if (!Objects.isNull(user.getUserEmail())) {
            if (StringUtils.hasText(user.getUserEmail())) {
                this.checkUserEmail(user);
            } else {
                throw new EstateException("User email cannot be empty!");
            }
        }

        // Update user.
        user.setUserId(userId);
        var result = this.updateById(user);
        if (!result) {
            throw new EstateException("Update is failed!");
        }
    }

    /**
     * The method for getting user list.
     *
     * @return Return the user list.
     */
    @Override
    public List<User> getUserList() throws EstateException {
        var userList = userMapper.selectList(null);
        // user list is empty.
        if (Objects.isNull(userList) || userList.size() == 0) {
            throw new EstateException("User list is empty!");
        }
        return userList;
    }

    /**
     * The method for viewing current user.
     *
     * @return Return the user.
     * @throws EstateException The EstateException object.
     */
    @Override
    public User viewCurrentUser() throws EstateException {
        // Get user id.
        var userId = this.getCurrentAuthenticationInformation();

        // Select user by user id.
        return this.selectUserByUserId(userId);
    }

    /**
     * The method for checking emails whether is valid.
     *
     * @param user The checking user.
     * @throws EstateException The EstateException object.
     */
    public void checkUserEmail(User user) throws EstateException {
        var userQueryWrapper = new LambdaQueryWrapper<User>();
        userQueryWrapper.eq(User::getUserEmail, user.getUserEmail());
        var count = userMapper.selectCount(userQueryWrapper);
        // User is existed!
        if (count > 0) {
            throw new EstateException("User email is existed!");
        }
    }

    /**
     * The method for selecting user by user id.
     *
     * @param userId The user id.
     * @throws EstateException The EstateException object.
     */
    public User selectUserByUserId(Long userId) throws EstateException {
        var userQueryWrapper = new LambdaQueryWrapper<User>();
        userQueryWrapper.eq(User::getUserId, userId);
        var user = userMapper.selectOne(userQueryWrapper);
        if (Objects.isNull(user)) {
            throw new EstateException("User is not existed!");
        }
        return user;
    }

    /**
     * The method for getting current authentication information.
     *
     * @return Return the user id.
     */
    public Long getCurrentAuthenticationInformation() {
        // Get the authentication information.
        var authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // Get the current user.
        var securityUser = (SecurityUser) authentication.getPrincipal();
        // Get user id.
        return securityUser.getUser().getUserId();
    }
}




