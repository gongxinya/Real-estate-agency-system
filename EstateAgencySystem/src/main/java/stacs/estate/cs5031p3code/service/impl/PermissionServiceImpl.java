package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.PermissionMapper;
import stacs.estate.cs5031p3code.mapper.RolePermissionMapper;
import stacs.estate.cs5031p3code.model.po.Permission;
import stacs.estate.cs5031p3code.model.po.RolePermission;
import stacs.estate.cs5031p3code.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The implement of service for database operations on the permission table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    /**
     * The permission mapper.
     */
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * The role_permission mapper.
     */
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * The method for creating a permission.
     *
     * @param permission The permission.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createPermission(Permission permission) throws EstateException {
        // If the permission properties are null.
        if (Objects.isNull(permission) || !StringUtils.hasText(permission.getPermissionName()) || !StringUtils.hasText(permission.getPermissionKey())) {
            throw new EstateException("Permission name or key cannot be empty!");
        }

        // Check permission name
        this.checkPermissionName(permission);

        // Insert Permission
        var result = permissionMapper.insert(permission);
        if (result < 1) {
            throw new EstateException("Create permission failed!");
        }
    }

    /**
     * The method for deleting permission by id.
     *
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void deletePermissionByPermissionId(Long permissionId) throws EstateException {
        // If permission id is null.
        if (Objects.isNull(permissionId)) {
            throw new EstateException("Permission id cannot be empty!");
        }

        // Get the permission by permission id.
        this.checkPermissionId(permissionId);

        // If role_permission connected, doesn't delete permission.
        var rolePermissionQueryWrapper = new LambdaQueryWrapper<RolePermission>();
        rolePermissionQueryWrapper.eq(RolePermission::getPermissionId, permissionId);
        var count = rolePermissionMapper.selectCount(rolePermissionQueryWrapper);
        if (count > 0) {
            throw new EstateException("Permission has relationship with roles, cannot remove!");
        }

        // Delete the permission in permission table.
        var permissionQueryWrapper = new LambdaQueryWrapper<Permission>();
        permissionQueryWrapper.eq(Permission::getPermissionId, permissionId);
        var result = permissionMapper.delete(permissionQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete permission is failed!");
        }
    }

    /**
     * The method for updating permission by id.
     *
     * @param permissionId The permission id.
     * @param permission   The permission.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void updatePermissionByPermissionId(Long permissionId, Permission permission) throws EstateException {
        // If the permission id is null or permission is null
        if (Objects.isNull(permissionId) || Objects.isNull(permission)) {
            throw new EstateException("Permission or permission id is empty!");
        }

        // Keep the uniqueness of permission name.
        if (!Objects.isNull(permission.getPermissionName())) {
            if (StringUtils.hasText(permission.getPermissionName())) {
                this.checkPermissionName(permission);
            } else {
                throw new EstateException("Permission name cannot be empty!");
            }
        }

        // Update permission.
        permission.setPermissionId(permissionId);
        var result = this.updateById(permission);
        if (!result) {
            throw new EstateException("Update is failed!");
        }
    }

    /**
     * The method for getting permission list.
     *
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Permission> getPermissionList() throws EstateException {
        var permissionList = permissionMapper.selectList(null);
        if (Objects.isNull(permissionList) || permissionList.size() == 0) {
            throw new EstateException("Permission list is empty!");
        }
        return permissionList;
    }

    /**
     * The method for checking permission name.
     *
     * @param permission The permission.
     * @throws EstateException The EstateException object.
     */
    public void checkPermissionName(Permission permission) throws EstateException {
        var permissionQueryWrapper = new LambdaQueryWrapper<Permission>();
        permissionQueryWrapper.eq(Permission::getPermissionName, permission.getPermissionName());
        var count = permissionMapper.selectCount(permissionQueryWrapper);
        if (count > 0) {
            throw new EstateException("Permission name is existed!");
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
}




