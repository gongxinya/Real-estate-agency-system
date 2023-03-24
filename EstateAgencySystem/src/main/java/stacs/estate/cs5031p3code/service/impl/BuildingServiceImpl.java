package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.service.BuildingService;
import stacs.estate.cs5031p3code.mapper.BuildingMapper;
import org.springframework.stereotype.Service;

/**
 * The implement of service for database operations on the building table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>
        implements BuildingService {

}




