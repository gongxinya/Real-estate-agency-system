package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.service.BuildingService;
import stacs.estate.cs5031p3code.mapper.BuildingMapper;
import org.springframework.stereotype.Service;

/**
* @author hz65
* @description 针对表【building(The building table)】的数据库操作Service实现
* @createDate 2023-03-23 22:22:51
*/
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>
    implements BuildingService{

}




