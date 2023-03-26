package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.FlatMapper;
import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.model.po.Flat;
import stacs.estate.cs5031p3code.service.BuildingService;
import stacs.estate.cs5031p3code.mapper.BuildingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The implement of service for database operations on the building table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>
        implements BuildingService {

    /**
     * The building mapper.
     */
    @Autowired
    private BuildingMapper buildingMapper;

    /**
     * The flat mapper.
     */
    @Autowired
    private FlatMapper flatMapper;

    /**
     * The method for creating building.
     *
     * @param building The building object.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createBuilding(Building building) throws EstateException {
        // If building properties are null.
        if (Objects.isNull(building)
                || !StringUtils.hasText(building.getBuildingName())
                || !StringUtils.hasText(building.getBuildingAddress())) {
            throw new EstateException("Building name or address cannot be empty!");
        }

        // Keep the uniqueness of building name.
        this.checkBuildingName(building);

        // Insert building
        var result = buildingMapper.insert(building);
        if (result < 1) {
            throw new EstateException("Create building failed!");
        }
    }

    /**
     * The method for deleting building by building id.
     *
     * @param buildingId The building id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void deleteBuildingById(Long buildingId) throws EstateException {
        // If building id is null.
        if (Objects.isNull(buildingId)) {
            throw new EstateException("Building id cannot be empty!");
        }

        // Get the building by building id.
        this.checkBuildingId(buildingId);

        // If building has flat, doesn't delete building.
        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getBuildingId, buildingId);
        var count = flatMapper.selectCount(flatQueryWrapper);
        if (count > 0) {
            throw new EstateException("Building has flats in there, cannot remove!");
        }

        // Delete the building in building table.
        var buildingQueryWrapper = new LambdaQueryWrapper<Building>();
        buildingQueryWrapper.eq(Building::getBuildingId, buildingId);
        var result = buildingMapper.delete(buildingQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete building is failed!");
        }
    }

    /**
     * The method for updating building by building id.
     *
     * @param buildingId The building id.
     * @param building   The building.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void updateBuildingByBuildingId(Long buildingId, Building building) throws EstateException {
        // If the building id is null or building is null
        if (Objects.isNull(buildingId) || Objects.isNull(building)) {
            throw new EstateException("Building or building id is empty!");
        }

        // Keep the uniqueness of building name.
        if (!Objects.isNull(building.getBuildingName())) {
            if (StringUtils.hasText(building.getBuildingName())) {
                this.checkBuildingName(building);
            } else {
                throw new EstateException("Building name cannot be empty!");
            }
        }

        // Update building.
        building.setBuildingId(buildingId);
        var result = this.updateById(building);
        if (!result) {
            throw new EstateException("Update is failed!");
        }
    }

    /**
     * The method for getting building list.
     *
     * @return Return the building list.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Building> getBuildingList() throws EstateException {
        var buildingList = buildingMapper.selectList(null);
        if (Objects.isNull(buildingList) || buildingList.size() == 0) {
            throw new EstateException("Building list is empty!");
        }
        return buildingList;
    }

    /**
     * The method for checking building names whether is valid.
     *
     * @param building The building object.
     * @throws EstateException The EstateException object.
     */
    public void checkBuildingName(Building building) throws EstateException {
        var buildingQueryWrapper = new LambdaQueryWrapper<Building>();
        buildingQueryWrapper.eq(Building::getBuildingName, building.getBuildingName());
        var count = buildingMapper.selectCount(buildingQueryWrapper);
        if (count > 0) {
            throw new EstateException("Building name is existed!");
        }
    }

    /**
     * The method for checking building id whether is existed.
     *
     * @param buildingId The building id.
     * @throws EstateException The EstateException object.
     */
    public void checkBuildingId(Long buildingId) throws EstateException {
        var buildingQueryWrapper = new LambdaQueryWrapper<Building>();
        buildingQueryWrapper.eq(Building::getBuildingId, buildingId);
        var building = buildingMapper.selectOne(buildingQueryWrapper);
        if (Objects.isNull(building)) {
            throw new EstateException("Building is not existed!");
        }
    }
}




