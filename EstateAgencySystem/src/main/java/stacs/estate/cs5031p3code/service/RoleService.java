package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the role table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface RoleService extends IService<Role> {

    /**
     * The method for creating a role.
     *
     * @param role The role.
     * @throws EstateException The EstateException object.
     */
    void createRole(Role role) throws EstateException;

    /**
     * The method for deleting role by id.
     *
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    void deleteRoleByRoleId(Long roleId) throws EstateException;

    /**
     * The method for updating role by id.
     *
     * @param roleId The role id.
     * @param role   The role.
     * @throws EstateException The EstateException object.
     */
    void updateRoleByRoleId(Long roleId, Role role) throws EstateException;

    /**
     * The method for getting role list.
     *
     * @return Return the role list.
     * @throws EstateException The EstateException object.
     */
    List<Role> getRoleList() throws EstateException;
}
