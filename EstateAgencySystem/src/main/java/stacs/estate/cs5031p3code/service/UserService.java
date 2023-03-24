package stacs.estate.cs5031p3code.service;

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
    Map<String, String> login(User user);

    /**
     * The method for logout.
     */
    void logout();
}
