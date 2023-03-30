package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.RolePermissionMapper;
import stacs.estate.cs5031p3code.mapper.UserRoleMapper;
import stacs.estate.cs5031p3code.model.po.*;
import stacs.estate.cs5031p3code.service.RoleService;
import stacs.estate.cs5031p3code.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The Service for database operations on the role table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * The role mapper.
     */
    @Autowired
    private RoleMapper roleMapper;

    /**
     * The user_role mapper.
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * The role_permission mapper.
     */
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * The method for creating a role.
     *
     * @param role The role.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createRole(Role role) throws EstateException {
        // If the role properties are null.
        if (Objects.isNull(role) || !StringUtils.hasText(role.getRoleName()) || !StringUtils.hasText(role.getRoleKey())) {
            throw new EstateException("Role name or key cannot be empty!");
        }

        // Check role name
        this.checkRoleName(role);

        // Insert Role
        var result = roleMapper.insert(role);
        if (result < 1) {
            throw new EstateException("Create role failed!");
        }
    }

    /**
     * The method for deleting role by id.
     *
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void deleteRoleByRoleId(Long roleId) throws EstateException {
        // If role id is null.
        if (Objects.isNull(roleId)) {
            throw new EstateException("Role id cannot be empty!");
        }

        // Get the role by role id.
        this.checkRoleId(roleId);

        // If user_role connected, doesn't delete role.
        var userRoleQueryWrapper = new LambdaQueryWrapper<UserRole>();
        userRoleQueryWrapper.eq(UserRole::getRoleId, roleId);
        var count = userRoleMapper.selectCount(userRoleQueryWrapper);
        if (count > 0) {
            throw new EstateException("Role has relationship with users, cannot remove!");
        }

        // If role_permission connected, doesn't delete role.
        var rolePermissionQueryWrapper = new LambdaQueryWrapper<RolePermission>();
        rolePermissionQueryWrapper.eq(RolePermission::getRoleId, roleId);
        count = rolePermissionMapper.selectCount(rolePermissionQueryWrapper);
        if (count > 0) {
            throw new EstateException("Role has relationship with permissions, cannot remove!");
        }

        // Delete the role in role table.
        var roleQueryWrapper = new LambdaQueryWrapper<Role>();
        roleQueryWrapper.eq(Role::getRoleId, roleId);
        var result = roleMapper.delete(roleQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete role is failed!");
        }
    }

    /**
     * The method for updating role by id.
     *
     * @param roleId The role id.
     * @param role   The role.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void updateRoleByRoleId(Long roleId, Role role) throws EstateException {
        // If the role id is null or role is null
        if (Objects.isNull(roleId) || Objects.isNull(role)) {
            throw new EstateException("Role or role id is empty!");
        }

        // Keep the uniqueness of role name.
        if (Objects.isNull(role.getRoleName()) || !StringUtils.hasText(role.getRoleName())) {
            throw new EstateException("Role name cannot be empty!");
        } else {
            this.checkRoleName(role);
        }

        // Update role.
        role.setRoleId(roleId);
        var result = this.updateById(role);
        if (!result) {
            throw new EstateException("Update is failed!");
        }
    }

    /**
     * The method for getting role list.
     *
     * @return Return the role list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Role> getRoleList() throws EstateException {
        var roleList = roleMapper.selectList(null);
        if (Objects.isNull(roleList) || roleList.size() == 0) {
            throw new EstateException("Role list is empty!");
        }
        return roleList;
    }

    /**
     * The method for checking role name.
     *
     * @param role The role.
     * @throws EstateException The EstateException object.
     */
    public void checkRoleName(Role role) throws EstateException {
        var roleQueryWrapper = new LambdaQueryWrapper<Role>();
        roleQueryWrapper.eq(Role::getRoleName, role.getRoleName());
        var roleNameList = roleMapper.selectList(roleQueryWrapper)
                .stream()
                .filter(role1 -> !Objects.equals(role.getRoleId(), role1.getRoleId()))
                .map(Role::getRoleName)
                .toList();
        if (roleNameList.contains(role.getRoleName())) {
            throw new EstateException("Role name is existed!");
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
}




