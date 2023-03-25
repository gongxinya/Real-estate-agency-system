package stacs.estate.cs5031p3code.service;

import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Flat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service for database operations on the flat table.
 *
 * @author 220032952
 * @version 0.0.1
 */
public interface FlatService extends IService<Flat> {

    /**
     * The method for creating flat by building id.
     *
     * @param buildingId The building id.
     * @param flat       The flat object.
     * @throws EstateException The EstateException object.
     */
    void createFlatByBuildingId(Long buildingId, Flat flat) throws EstateException;

    /**
     * The method for deleting flat by flat id.
     *
     * @param flatId The flat id.
     * @throws EstateException The EstateException object.
     */
    void deleteFlatByFlatId(Long flatId) throws EstateException;

    /**
     * The method for updating flat by flat id.
     *
     * @param flatId The flat id.
     * @param flat   The flat.
     * @throws EstateException The EstateException object.
     */
    void updateFlatByFlatId(Long flatId, Flat flat) throws EstateException;

    /**
     * The method for getting flat list.
     *
     * @return Return the list of flats.
     * @throws EstateException The EstateException object.
     */
    List<Flat> getFlatList() throws EstateException;

    /**
     * The method for getting flat list by building id.
     *
     * @param buildingId The building id.
     * @return Return the list of flat.
     * @throws EstateException The EstateException object.
     */
    List<Flat> getFlatListByBuildingId(Long buildingId) throws EstateException;
}
