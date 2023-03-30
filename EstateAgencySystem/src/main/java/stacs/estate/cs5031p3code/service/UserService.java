package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Service for database operations on the user table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface UserService extends IService<User> {

    /**
     * The method for login.
     *
     * @param user The user information.
     * @return Return a user_key.
     */
    Map<String, String> login(User user) throws EstateException;

    /**
     * The method for logout.
     */
    void logout() throws EstateException;

    /**
     * The method for register.
     *
     * @param user The user object.
     * @throws EstateException The EstateException object.
     */
    void register(User user) throws EstateException;

    /**
     * The method for deleting user by userId.
     *
     * @param userId The user id object.
     * @throws EstateException The EstateException object.
     */
    void deleteUserById(Long userId) throws EstateException;

    /**
     * The method for updating user.
     *
     * @param user The user.
     * @throws EstateException The EstateException user.
     */
    void updateUser(User user) throws EstateException;

    /**
     * The method for updating user by user id.
     *
     * @param userId The user id.
     * @param user   The updated user.
     * @throws EstateException The EstateException object.
     */
    void updateUserByUserId(Long userId, User user) throws EstateException;

    /**
     * The method for getting user list.
     *
     * @return Return the user list.
     * @throws EstateException The EstateException object.
     */
    List<User> getUserList() throws EstateException;

    /**
     * The method for viewing current user.
     *
     * @return Return the user.
     * @throws EstateException The EstateException object.
     */
    User viewCurrentUser() throws EstateException;
}
