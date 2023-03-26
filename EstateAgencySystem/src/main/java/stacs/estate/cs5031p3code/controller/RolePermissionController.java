package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Permission;
import stacs.estate.cs5031p3code.service.RolePermissionService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about RolePermission
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/role/permission")
public class RolePermissionController {

    /**
     * The role permission service.
     */
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * The method for assigning permission to role.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @return Return the result.
     */
    @PostMapping("/{roleId}/{permissionId}")
    @PreAuthorize("hasAuthority('role:permission:create')")
    public ResponseResult<Void> assignPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        try {
            this.rolePermissionService.createPermissionToRole(roleId, permissionId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Assign permission to role successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for removing permission to user.
     *
     * @param roleId       The role id.
     * @param permissionId The permission id.
     * @return Return the result.
     */
    @DeleteMapping("/{roleId}/{permissionId}")
    @PreAuthorize("hasAuthority('role:permission:delete')")
    public ResponseResult<Void> removePermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        try {
            this.rolePermissionService.removePermissionToRole(roleId, permissionId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Remove permission to role successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for getting permission list by role.
     *
     * @param roleId The role id.
     * @return Return result.
     */
    @GetMapping("/list/{roleId}")
    @PreAuthorize("hasAuthority('role:permission:list')")
    public ResponseResult<List<Permission>> listAllRolesByRoleId(@PathVariable Long roleId) {
        List<Permission> permissionList;
        try {
            permissionList = this.rolePermissionService.listPermissionsByRoleId(roleId);
        } catch (EstateException e) {
            return ResponseResult.<List<Permission>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<Permission>>builder()
                .data(permissionList)
                .message("Getting permission list for role successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for getting permission list by user.
     *
     * @param userId The user id.
     * @return Return the permission list.
     */
    @GetMapping("/list/{userId}")
    @PreAuthorize("hasAuthority('user:permission:list')")
    public ResponseResult<List<Permission>> listAllRolesByUserId(@PathVariable Long userId) {
        List<Permission> permissionList;
        try {
            permissionList = this.rolePermissionService.listPermissionsByUserId(userId);
        } catch (EstateException e) {
            return ResponseResult.<List<Permission>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<Permission>>builder()
                .data(permissionList)
                .message("Getting permission list for user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
