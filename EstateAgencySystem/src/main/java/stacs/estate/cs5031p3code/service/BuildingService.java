package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Building;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the building table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface BuildingService extends IService<Building> {

    /**
     * The method for creating building.
     *
     * @param building The building object.
     * @throws EstateException The EstateException object.
     */
    void createBuilding(Building building) throws EstateException;

    /**
     * The method for deleting building by building id.
     *
     * @param buildingId The building id.
     * @throws EstateException The EstateException object.
     */
    void deleteBuildingById(Long buildingId) throws EstateException;

    /**
     * The method for updating building by building id.
     *
     * @param buildingId The building id.
     * @param building   The building.
     * @throws EstateException The EstateException object.
     */
    void updateBuildingByBuildingId(Long buildingId, Building building) throws EstateException;

    /**
     * The method for getting building list.
     *
     * @return Return the building list.
     * @throws EstateException The EstateException object.
     */
    List<Building> getBuildingList() throws EstateException;
}
