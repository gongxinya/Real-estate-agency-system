package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the permission table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface PermissionService extends IService<Permission> {
    /**
     * The method for creating a permission.
     *
     * @param permission The permission.
     * @throws EstateException The EstateException object.
     */
    void createPermission(Permission permission) throws EstateException;

    /**
     * The method for deleting permission by id.
     *
     * @param permissionId The permission id.
     * @throws EstateException The EstateException object.
     */
    void deletePermissionByPermissionId(Long permissionId) throws EstateException;

    /**
     * The method for updating permission by id.
     *
     * @param permissionId The permission id.
     * @param permission   The permission.
     * @throws EstateException The EstateException object.
     */
    void updatePermissionByPermissionId(Long permissionId, Permission permission) throws EstateException;

    /**
     * The method for getting permission list.
     *
     * @return Return the permission list.
     * @throws EstateException The EstateException object.
     */
    List<Permission> getPermissionList() throws EstateException;
}
