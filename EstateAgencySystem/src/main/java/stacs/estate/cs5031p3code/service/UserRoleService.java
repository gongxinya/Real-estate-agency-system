package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.model.po.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the user_role table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * The method for create a role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    void createRoleToUser(Long userId, Long roleId) throws EstateException;

    /**
     * The method for remove a role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @throws EstateException The EstateException object.
     */
    void removeRoleToUser(Long userId, Long roleId) throws EstateException;

    /**
     * The method for getting role by user id.
     *
     * @param userId The user id.
     * @return Return the role list.
     * @throws EstateException The EstateException object.
     */
    List<Role> listRolesByUserId(Long userId) throws EstateException;
}
