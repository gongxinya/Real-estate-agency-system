package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.service.RoleService;
import stacs.estate.cs5031p3code.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author hz65
* @description 针对表【role(The role table)】的数据库操作Service实现
* @createDate 2023-03-23 22:22:51
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




