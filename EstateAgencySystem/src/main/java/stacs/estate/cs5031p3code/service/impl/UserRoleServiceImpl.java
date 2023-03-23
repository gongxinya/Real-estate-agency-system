package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.UserRole;
import stacs.estate.cs5031p3code.service.UserRoleService;
import stacs.estate.cs5031p3code.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author hz65
* @description 针对表【user_role(The table for connecting user and role)】的数据库操作Service实现
* @createDate 2023-03-23 22:22:51
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




