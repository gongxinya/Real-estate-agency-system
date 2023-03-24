package stacs.estate.cs5031p3code.mapper;

import org.springframework.stereotype.Repository;
import stacs.estate.cs5031p3code.model.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author hz65
* @description 针对表【user(The user table)】的数据库操作Mapper
* @createDate 2023-03-23 22:22:51
* @Entity stacs.estate.cs5031p3code.model.po.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




