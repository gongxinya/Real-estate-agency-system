package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Role;
import stacs.estate.cs5031p3code.service.UserRoleService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;

/**
 * A class for handling with all API about RoleUser.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/user/role")
public class UserRoleController {

    /**
     * The user role service.
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * The method for assigning role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @return Return the result.
     */
    @PostMapping("/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('user:role:create')")
    public ResponseResult<Void> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            this.userRoleService.createRoleToUser(userId, roleId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Assign role to user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for removing role to user.
     *
     * @param userId The user id.
     * @param roleId The role id.
     * @return Return the result.
     */
    @DeleteMapping("/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('user:role:delete')")
    public ResponseResult<Void> removeRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            this.userRoleService.removeRoleToUser(userId, roleId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Remove role to user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for getting role list by user.
     *
     * @param userId The user id.
     * @return Return result.
     */
    @GetMapping("/list/{userId}")
    @PreAuthorize("hasAuthority('user:role:list')")
    public ResponseResult<List<Role>> listAllRolesByUserId(@PathVariable Long userId) {
        List<Role> roleList;
        try {
            roleList = this.userRoleService.listRolesByUserId(userId);
        } catch (EstateException e) {
            return ResponseResult.<List<Role>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<Role>>builder()
                .data(roleList)
                .message("Getting role list for user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
