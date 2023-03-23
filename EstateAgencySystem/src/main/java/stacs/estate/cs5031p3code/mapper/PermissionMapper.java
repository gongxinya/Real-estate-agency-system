package stacs.estate.cs5031p3code.mapper;

import stacs.estate.cs5031p3code.model.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hz65
* @description 针对表【permission(The permission table)】的数据库操作Mapper
* @createDate 2023-03-23 22:22:51
* @Entity stacs.estate.cs5031p3code.model.po.Permission
*/
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermisssionsByUserId(Long userId);
}




