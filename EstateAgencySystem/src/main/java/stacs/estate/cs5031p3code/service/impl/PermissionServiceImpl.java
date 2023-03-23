package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.Permission;
import stacs.estate.cs5031p3code.service.PermissionService;
import stacs.estate.cs5031p3code.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author hz65
* @description 针对表【permission(The permission table)】的数据库操作Service实现
* @createDate 2023-03-23 22:22:51
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




