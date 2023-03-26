package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.mapper.BuildingMapper;
import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.model.po.Flat;
import stacs.estate.cs5031p3code.service.FlatService;
import stacs.estate.cs5031p3code.mapper.FlatMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The implement of service for database operations on the flat table.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Service
public class FlatServiceImpl extends ServiceImpl<FlatMapper, Flat>
        implements FlatService {

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
     * The method for creating flat by building id.
     *
     * @param buildingId The building id.
     * @param flat       The flat object.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void createFlatByBuildingId(Long buildingId, Flat flat) throws EstateException {
        // If the flat properties are null.
        if (Objects.isNull(buildingId)
                || Objects.isNull(flat)
                || !StringUtils.hasText(flat.getFlatName())
                || Objects.isNull(flat.getFlatArea())) {
            throw new EstateException("Building id, flat name or area cannot be empty!");
        }
        flat.setBuildingId(buildingId);

        // Check the building id.
        this.checkBuildingId(buildingId);

        // Check the flat name in a building
        this.checkFlatName(buildingId, flat);

        // Insert flat
        var result = flatMapper.insert(flat);
        if (result < 1) {
            throw new EstateException("Create flat failed!");
        }
    }

    /**
     * The method for deleting flat by flat id.
     *
     * @param flatId The flat id.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void deleteFlatByFlatId(Long flatId) throws EstateException {
        // If the flat id is null
        if (Objects.isNull(flatId)) {
            throw new EstateException("Flat id cannot be empty!");
        }

        // Get the flat by flat id.
        this.selectFlatByFlatId(flatId);

        // If there have the connection of user and flat, doesn't delete flat.
        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getFlatId, flatId)
                .isNotNull(Flat::getUserId);
        var count = flatMapper.selectCount(flatQueryWrapper);
        if (count > 0) {
            throw new EstateException("Flat has users in there, cannot remove!");
        }

        // Delete the flat in flat table.
        flatQueryWrapper.clear();
        flatQueryWrapper.eq(Flat::getFlatId, flatId);
        var result = flatMapper.delete(flatQueryWrapper);
        if (result < 1) {
            throw new EstateException("Delete flat is failed!");
        }
    }

    /**
     * The method for updating flat by flat id.
     *
     * @param flatId The flat id.
     * @param flat   The flat.
     * @throws EstateException The EstateException object.
     */
    @Override
    public void updateFlatByFlatId(Long flatId, Flat flat) throws EstateException {
        // If the flat id is null or flat is null
        if (Objects.isNull(flat) || Objects.isNull(flatId)) {
            throw new EstateException("Flat or flat id is empty!");
        }

        // Check building id.
        var oldFlat = this.selectFlatByFlatId(flatId);
        if (!Objects.isNull(flat.getBuildingId())) {
            this.checkBuildingId(flat.getBuildingId());
        } else {
            flat.setBuildingId(oldFlat.getBuildingId());
        }

        // Keep the uniqueness of flat name.
        if (!Objects.isNull(flat.getFlatName())) {
            if (StringUtils.hasText(flat.getFlatName())) {
                this.checkFlatName(flat.getBuildingId(), flat);
            } else {
                throw new EstateException("Flat name cannot be empty!");
            }
        }

        // Update flat.
        flat.setFlatId(flatId);
        var result = this.updateById(flat);
        if (!result) {
            throw new EstateException("Update is failed!");
        }
    }

    /**
     * The method for getting flat list.
     *
     * @return Return the list of flats.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Flat> getFlatList() throws EstateException {
        var flatList = flatMapper.selectList(null);
        if (Objects.isNull(flatList) || flatList.size() == 0) {
            throw new EstateException("Flat list is empty!");
        }
        return flatList;
    }

    /**
     * The method for getting flat list by building id.
     *
     * @param buildingId The building id.
     * @return Return the list of flat.
     * @throws EstateException The EstateException object.
     */
    @Override
    public List<Flat> getFlatListByBuildingId(Long buildingId) throws EstateException {
        // Building id is null
        if (Objects.isNull(buildingId)) {
            throw new EstateException("Building id cannot be empty!");
        }

        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getBuildingId, buildingId);
        var flatList = flatMapper.selectList(flatQueryWrapper);
        if (Objects.isNull(flatList) || flatList.size() == 0) {
            throw new EstateException("Flat list is empty!");
        }
        return flatList;
    }

    /**
     * The method for checking the flat name.
     * In a building, no duplicated flat name.
     *
     * @param buildingId The building id.
     * @param flat       The flat.
     */
    public void checkFlatName(Long buildingId, Flat flat) throws EstateException {
        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getBuildingId, buildingId);
        var flatNameList = flatMapper.selectList(flatQueryWrapper)
                .stream()
                .map(Flat::getFlatName)
                .toList();
        if (flatNameList.contains(flat.getFlatName())) {
            throw new EstateException("Flat name is existed in a certain building!");
        }
    }

    /**
     * The method for checking the building id whether is existed.
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

    /**
     * The method for selecting flat by flat id.
     *
     * @param flatId The flat id.
     * @throws EstateException The EstateException object.
     */
    public Flat selectFlatByFlatId(Long flatId) throws EstateException {
        var flatQueryWrapper = new LambdaQueryWrapper<Flat>();
        flatQueryWrapper.eq(Flat::getFlatId, flatId);
        var flat = flatMapper.selectOne(flatQueryWrapper);
        if (Objects.isNull(flat)) {
            throw new EstateException("Flat is not existed!");
        }
        return flat;
    }
}




