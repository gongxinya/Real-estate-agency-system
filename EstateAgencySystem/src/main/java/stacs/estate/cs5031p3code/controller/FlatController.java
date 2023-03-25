package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Flat;
import stacs.estate.cs5031p3code.service.FlatService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about flat.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/flat")
public class FlatController {

    /**
     * The flat service.
     */
    @Autowired
    private FlatService flatService;

    /**
     * The method for creating flat by building id.
     *
     * @param buildingId The building id.
     * @param flat       The flat object.
     * @return Return the result.
     */
    @PostMapping("/create/{buildingId}")
    @PreAuthorize("hasAuthority('flat:create')")
    public ResponseResult<Void> createFlatByBuildingId(@PathVariable Long buildingId, @RequestBody Flat flat) {
        try {
            this.flatService.createFlatByBuildingId(buildingId, flat);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Create successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for deleting flat by flat id.
     *
     * @param flatId The flat id.
     * @return Return the result.
     */
    @DeleteMapping("/delete/{flatId}")
    @PreAuthorize("hasAuthority('flat:delete')")
    public ResponseResult<Void> deleteFlatByFlatId(@PathVariable Long flatId) {
        try {
            this.flatService.deleteFlatByFlatId(flatId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Delete successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for updating flat by flat id.
     *
     * @param flatId The flat id.
     * @param flat   The updated flat.
     * @return Return the result.
     */
    @PutMapping("/update/{flatId}")
    @PreAuthorize("hasAuthority('flat:update')")
    public ResponseResult<Void> updateFlatByFlatId(@PathVariable Long flatId, @RequestBody Flat flat) {
        try {
            this.flatService.updateFlatByFlatId(flatId, flat);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Update successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for listing all flats.
     *
     * @return Return the flat list.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('flat:list')")
    public ResponseResult<List<Flat>> listFlat() {
        List<Flat> flatList;
        try {
            flatList = this.flatService.getFlatList();
        } catch (EstateException e) {
            return ResponseResult.<List<Flat>>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<List<Flat>>builder().data(flatList).message("Getting flat list is successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for list all flats by building id.
     *
     * @param buildingId The building id.
     * @return Return the flat list by building id.
     */
    @GetMapping("/list/{buildingId}")
    @PreAuthorize("hasAuthority('flat:list')")
    public ResponseResult<List<Flat>> listFlatByBuildingId(@PathVariable Long buildingId) {
        List<Flat> flatList;
        try {
            flatList = this.flatService.getFlatListByBuildingId(buildingId);
        } catch (EstateException e) {
            return ResponseResult.<List<Flat>>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<List<Flat>>builder().data(flatList).message("Getting flat list is successful!").code(HttpStatus.OK.value()).build();
    }
}
