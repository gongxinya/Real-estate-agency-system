package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.service.RoleService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about role.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * The role service.
     */
    @Autowired
    private RoleService roleService;

    /**
     * The method for creating role.
     *
     * @param role The role.
     * @return Return the result.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('role:create')")
    public ResponseResult<Void> createRole(@RequestBody Role role) {
        try {
            this.roleService.createRole(role);
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
     * The method for deleting role.
     *
     * @param roleId The role id.
     * @return Return the result.
     */
    @DeleteMapping("/delete/{roleId}")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseResult<Void> deleteRoleByRoleId(@PathVariable Long roleId) {
        try {
            this.roleService.deleteRoleByRoleId(roleId);
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
     * The method for updating role by role id.
     *
     * @param roleId role id.
     * @return Return the result.
     */
    @PutMapping("/update/{roleId}")
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseResult<Void> updateRoleByRoleId(@PathVariable Long roleId, @RequestBody Role role) {
        try {
            this.roleService.updateRoleByRoleId(roleId, role);
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
     * The method for getting role list.
     *
     * @return Return the role list.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('role:list')")
    public ResponseResult<List<Role>> listRole() {
        List<Role> roleList;
        try {
            roleList = this.roleService.getRoleList();
        } catch (EstateException e) {
            return ResponseResult.<List<Role>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<Role>>builder()
                .data(roleList)
                .message("Getting role list is successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
