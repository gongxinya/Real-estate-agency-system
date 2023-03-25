package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Permission;
import stacs.estate.cs5031p3code.service.PermissionService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about permission.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    /**
     * The permission service.
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * The method for creating permission.
     *
     * @param permission The permission.
     * @return Return the result.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('permission:create')")
    public ResponseResult<Void> createPermission(@RequestBody Permission permission) {
        try {
            this.permissionService.createPermission(permission);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Create successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for deleting permission.
     *
     * @param permissionId The permission id.
     * @return Return the result.
     */
    @DeleteMapping("/delete/{permissionId}")
    @PreAuthorize("hasAuthority('permission:delete')")
    public ResponseResult<Void> deletePermissionByPermissionId(@PathVariable Long permissionId) {
        try {
            this.permissionService.deletePermissionByPermissionId(permissionId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Delete successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for updating permission by permission id.
     *
     * @param permissionId permission id.
     * @return Return the result.
     */
    @PutMapping("/update/{permissionId}")
    @PreAuthorize("hasAuthority('permission:update')")
    public ResponseResult<Void> updateBuildingByBuildingId(@PathVariable Long permissionId, @RequestBody Permission permission) {
        try {
            this.permissionService.updatePermissionByPermissionId(permissionId, permission);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<Void>builder().data(null).message("Update successful!").code(HttpStatus.OK.value()).build();
    }

    /**
     * The method for getting permission list.
     *
     * @return Return the permission list.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('permission:list')")
    public ResponseResult<List<Permission>> listPermission() {
        List<Permission> permissionList;
        try {
            permissionList = this.permissionService.getPermissionList();
        } catch (EstateException e) {
            return ResponseResult.<List<Permission>>builder().data(null).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseResult.<List<Permission>>builder().data(permissionList).message("Getting permission list is successful!").code(HttpStatus.OK.value()).build();
    }
}
