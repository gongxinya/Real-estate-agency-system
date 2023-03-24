package stacs.estate.cs5031p3code.mapper;

import org.springframework.stereotype.Repository;
import stacs.estate.cs5031p3code.model.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * Mapper for database operations on the table [permission].
 *
 * @author 220032952
 * @version 0.0.1
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermisssionsByUserId(Long userId);
}




