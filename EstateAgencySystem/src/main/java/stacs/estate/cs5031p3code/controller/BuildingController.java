package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.service.BuildingService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about building.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/building")
public class BuildingController {

    /**
     * The building service.
     */
    @Autowired
    private BuildingService buildingService;

    /**
     * The method for creating new building.
     *
     * @param building The building object.
     * @return Return the result.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('building:create')")
    public ResponseResult<Void> createBuilding(@RequestBody Building building) {
        try {
            this.buildingService.createBuilding(building);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Create successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for deleting building by building id.
     *
     * @param buildingId The building id.
     * @return Return the result.
     */
    @DeleteMapping("/delete/{buildingId}")
    @PreAuthorize("hasAuthority('building:delete')")
    public ResponseResult<Void> deleteBuilding(@PathVariable Long buildingId) {
        try {
            this.buildingService.deleteBuildingById(buildingId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Delete successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for updating building by building id.
     *
     * @param buildingId The building id.
     * @return Return the result.
     */
    @PutMapping("/update/{buildingId}")
    @PreAuthorize("hasAuthority('building:update')")
    public ResponseResult<Void> updateBuildingByBuildingId(@PathVariable Long buildingId, @RequestBody Building building) {
        try {
            this.buildingService.updateBuildingByBuildingId(buildingId, building);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Update successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for getting building list.
     *
     * @return Return the building list.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('building:list')")
    public ResponseResult<List<Building>> listBuilding() {
        List<Building> buildingList;
        try {
            buildingList = this.buildingService.getBuildingList();
        } catch (EstateException e) {
            return ResponseResult.<List<Building>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<Building>>builder()
                .data(buildingList)
                .message("Getting building list is successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
