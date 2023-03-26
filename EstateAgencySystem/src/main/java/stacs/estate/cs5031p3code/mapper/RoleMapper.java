package stacs.estate.cs5031p3code.mapper;

import org.springframework.stereotype.Repository;
import stacs.estate.cs5031p3code.model.po.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * Mapper for database operations on the table [role].
 *
 * @author 220032952
 * @version 0.0.1
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * The method for getting all roles for user id.
     *
     * @param userId The user id.
     * @return Return the role list.
     */
    List<Role> selectRolesByUserId(Long userId);
}




