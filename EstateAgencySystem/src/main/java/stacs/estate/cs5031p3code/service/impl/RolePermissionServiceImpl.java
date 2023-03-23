package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.RolePermission;
import stacs.estate.cs5031p3code.service.RolePermissionService;
import stacs.estate.cs5031p3code.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author hz65
* @description 针对表【role_permission(The table for connecting role and permission)】的数据库操作Service实现
* @createDate 2023-03-23 22:22:51
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




