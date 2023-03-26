package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.RoleMapper;
import stacs.estate.cs5031p3code.mapper.UserMapper;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.model.po.UserRole;
import stacs.estate.cs5031p3code.service.UserRoleService;
import stacs.estate.cs5031p3code.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The implement of service for database operations on the user_role table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    /**
     * The user role mapper.
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

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
     * The method for creating a role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createRoleToUser(Long userId, Long roleId) throws EstateException {
        // If the user id or role id is null.
        if (Objects.isNull(userId) || Objects.isNull(roleId)) {
            throw new EstateException("The id of user or role cannot be empty!");
        }

        // If no user with user id.
        this.checkUserId(userId);

        // If no role with role id.
        this.checkRoleId(roleId);

        // If the relationship is existed.
        this.checkUserIdAndRoleId(userId, roleId);

        // Insert the relationship of role and user.
        var result = userRoleMapper.insert(UserRole.builder().userId(userId).roleId(roleId).build());
        if (result < 1) {
            throw new EstateException("Create role to user failed!");
        }
    }

    /**
     * The method for removing a role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void removeRoleToUser(Long userId, Long roleId) throws EstateException {
        // If the user id or role id is null.
        if (Objects.isNull(userId) || Objects.isNull(roleId)) {
            throw new EstateException("The id of user or role cannot be empty!");
        }

        // If no user with user id.
        this.checkUserId(userId);

        // If no role with role id.
        this.checkRoleId(roleId);

        // Delete relationship of role and user.
        var userRoleQueryWrapper = new LambdaQueryWrapper<UserRole>();
        userRoleQueryWrapper.eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId);
        var result = userRoleMapper.delete(userRoleQueryWrapper);
        if (result < 1) {
            throw new EstateException("Remove role to user failed!");
        }
    }

    /**
     * The method for getting role by user id.
     *
     * @param userId The user id.
     * @return Return the role list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Role> listRolesByUserId(Long userId) throws EstateException {
        // If user id is null.
        if (Objects.isNull(userId)) {
            throw new EstateException("The id of user cannot be empty!");
        }

        // If the user is not existed.
        this.checkUserId(userId);

        // Get the role list.
        var roleList = roleMapper.selectRolesByUserId(userId);
        if (Objects.isNull(roleList) || roleList.size() == 0) {
            throw new EstateException("Role list is empty!");
        }
        return roleList;
    }

    /**
     * The method for checking user id whether is existed.
     *
     * @param userId The user id.
     * @throws EstateException The EstateException object.
     */
    public void checkUserId(Long userId) throws EstateException {
        var userQueryWrapper = new LambdaQueryWrapper<User>();
        userQueryWrapper.eq(User::getUserId, userId);
        var user = userMapper.selectOne(userQueryWrapper);
        if (Objects.isNull(user)) {
            throw new EstateException("User is not existed!");
        }
    }

    /**
     * The method for checking role id whether is existed.
     *
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    public void checkRoleId(Long roleId) throws EstateException {
        var roleQueryWrapper = new LambdaQueryWrapper<Role>();
        roleQueryWrapper.eq(Role::getRoleId, roleId);
        var role = roleMapper.selectOne(roleQueryWrapper);
        if (Objects.isNull(role)) {
            throw new EstateException("Role is not existed!");
        }
    }

    /**
     * The method for checking the relationship
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    public void checkUserIdAndRoleId(Long userId, Long roleId) throws EstateException {
        var userRoleQueryWrapper = new LambdaQueryWrapper<UserRole>();
        userRoleQueryWrapper.eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId);
        var userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        if (!Objects.isNull(userRole)) {
            throw new EstateException("The relationship of user and role is existed!");
        }
    }
}




