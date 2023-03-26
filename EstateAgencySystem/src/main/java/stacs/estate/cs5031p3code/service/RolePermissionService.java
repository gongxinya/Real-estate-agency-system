package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Permission;
import stacs.estate.cs5031p3code.model.po.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the role_permission table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * The method for creating a permission to role.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    void createPermissionToRole(Long roleId, Long permissionId) throws EstateException;

    /**
     * The method for removing a permission to role.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    void removePermissionToRole(Long roleId, Long permissionId) throws EstateException;

    /**
     * The method for getting permission by role id.
     *
     * @param roleId The role id.
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    List<Permission> listPermissionsByRoleId(Long roleId) throws EstateException;

    /**
     * The method for getting permission by user id.
     *
     * @param userId The user id.
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    List<Permission> listPermissionsByUserId(Long userId) throws EstateException;
}
