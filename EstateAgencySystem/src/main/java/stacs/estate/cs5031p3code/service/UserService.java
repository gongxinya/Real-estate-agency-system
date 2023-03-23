package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author hz65
* @description 针对表【user(The user table)】的数据库操作Service
* @createDate 2023-03-23 22:22:51
*/
public interface UserService extends IService<User> {

    Map<String, String> login(User user);

    void logout();
}
