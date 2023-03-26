package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.PermissionMapper;
import stacs.estate.cs5031p3code.mapper.RoleMapper;
import stacs.estate.cs5031p3code.mapper.UserMapper;
import stacs.estate.cs5031p3code.model.po.*;
import stacs.estate.cs5031p3code.service.RolePermissionService;
import stacs.estate.cs5031p3code.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The implement of service for database operations on the role_permission table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionService {

    /**
     * The role permission mapper.
     */
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * The role mapper.
     */
    @Autowired
    private RoleMapper roleMapper;

    /**
     * The permission mapper.
     */
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * The user mapper.
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * The method for creating a permission to role.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createPermissionToRole(Long roleId, Long permissionId) throws EstateException {
        // If the role id and permission id is null.
        if (Objects.isNull(roleId) || Objects.isNull(permissionId)) {
            throw new EstateException("The id of role or permission cannot be empty!");
        }

        // If no role with role id.
        this.checkRoleId(roleId);

        // If no permission with permission id.
        this.checkPermissionId(permissionId);

        // If the relationship is existed.
        this.checkRoleIdAndPermissionId(roleId, permissionId);

        // Insert the relationship of role and permission.
        var result = rolePermissionMapper.insert(RolePermission.builder().roleId(roleId).permissionId(permissionId).build());
        if (result < 1) {
            throw new EstateException("Create permission to role failed!");
        }
    }

    /**
     * The method for removing a permission to role.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void removePermissionToRole(Long roleId, Long permissionId) throws EstateException {
        // If the user id or role id is null.
        if (Objects.isNull(roleId) || Objects.isNull(permissionId)) {
            throw new EstateException("The id of role or permission cannot be empty!");
        }

        // If no role with role id.
        this.checkRoleId(roleId);

        // If no permission with permission id.
        this.checkPermissionId(permissionId);

        // Delete relationship of role and user.
        var rolePermissionQueryWrapper = new LambdaQueryWrapper<RolePermission>();
        rolePermissionQueryWrapper.eq(RolePermission::getRoleId, roleId).eq(RolePermission::getPermissionId, permissionId);
        var result = rolePermissionMapper.delete(rolePermissionQueryWrapper);
        if (result < 1) {
            throw new EstateException("Remove permission to role failed!");
        }
    }

    /**
     * The method for getting permission by role id.
     *
     * @param roleId The role id.
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Permission> listPermissionsByRoleId(Long roleId) throws EstateException {
        // If role id is null.
        if (Objects.isNull(roleId)) {
            throw new EstateException("The id of role cannot be empty!");
        }

        // If the role is not existed.
        this.checkRoleId(roleId);

        // Get the permission list.
        var permissionList = permissionMapper.selectPermissionsByRoleId(roleId);
        if (Objects.isNull(permissionList) || permissionList.size() == 0) {
            throw new EstateException("Permission list is empty!");
        }
        return permissionList;
    }

    /**
     * The method for getting permission by user id.
     *
     * @param userId The user id.
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Permission> listPermissionsByUserId(Long userId) throws EstateException {
        // If user id is null.
        if (Objects.isNull(userId)) {
            throw new EstateException("The id of user cannot be empty!");
        }

        // If the user is not existed.
        this.checkUserId(userId);

        // Get the role list.
        var permissionList = permissionMapper.selectPermissionsByUserId(userId);
        if (Objects.isNull(permissionList) || permissionList.size() == 0) {
            throw new EstateException("Permission list is empty!");
        }
        return permissionList;
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
     * The method for checking permission id whether is existed.
     *
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    public void checkPermissionId(Long permissionId) throws EstateException {
        var permissionQueryWrapper = new LambdaQueryWrapper<Permission>();
        permissionQueryWrapper.eq(Permission::getPermissionId, permissionId);
        var permission = permissionMapper.selectOne(permissionQueryWrapper);
        if (Objects.isNull(permission)) {
            throw new EstateException("Permission is not existed!");
        }
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
     * The method for checking the relationship.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    public void checkRoleIdAndPermissionId(Long roleId, Long permissionId) throws EstateException {
        var rolePermissionQueryWrapper = new LambdaQueryWrapper<RolePermission>();
        rolePermissionQueryWrapper.eq(RolePermission::getRoleId, roleId).eq(RolePermission::getPermissionId, permissionId);
        var rolePermission = rolePermissionMapper.selectOne(rolePermissionQueryWrapper);
        if (!Objects.isNull(rolePermission)) {
            throw new EstateException("The relationship of role and permission is existed!");
        }
    }
}




