package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.service.UserService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class for handling with all API about user.
 *
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Getting the UserService.
     */
    @Autowired
    private UserService userService;

    /**
     * The method for handling with login API.
     *
     * @param user The User object.
     * @return Return result.
     */
    @PostMapping("/login")
    public ResponseResult<Map<String, String>> login(@RequestBody User user) {
        if (Objects.isNull(user)
                || !StringUtils.hasText(user.getUserEmail())
                || !StringUtils.hasText(user.getUserPassword())) {
            return ResponseResult.<Map<String, String>>builder()
                    .data(null)
                    .message("User email or password cannot be empty!")
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
        return ResponseResult.<Map<String, String>>builder()
                .data(userService.login(user))
                .message("Login successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for handling with logout API.
     *
     * @return Return result.
     */
    @GetMapping("/logout")
    public ResponseResult<Void> logout() {
        this.userService.logout();
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Logout successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for registering a new user.
     *
     * @return Return result.
     */
    @PostMapping("/create")
    public ResponseResult<Void> register(@RequestBody User user) {
        try {
            this.userService.register(user);
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
     * The method for deleting user by user id.
     *
     * @param userId The user id.
     * @return Return the result.
     */
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseResult<Void> deleteUser(@PathVariable Long userId) {
        try {
            this.userService.deleteUserById(userId);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Delete user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for updating user.
     * All users have this authority.
     *
     * @param user The user.
     * @return Return the result.
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user:update:self')")
    public ResponseResult<Void> updateUser(@RequestBody User user) {
        try {
            this.userService.updateUser(user);
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
     * The method for updating user by user id.
     * Only admin has this authority.
     *
     * @param userId The user id.
     * @param user   The user.
     * @return Return the result.
     */
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('user:update:other')")
    public ResponseResult<Void> updateUserById(@PathVariable Long userId, @RequestBody User user) {
        try {
            this.userService.updateUserByUserId(userId, user);
        } catch (EstateException e) {
            return ResponseResult.<Void>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Update user successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for getting user list.
     *
     * @return Return the user list.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public ResponseResult<List<User>> listUser() {
        List<User> userList;
        try {
            userList = userService.getUserList();
        } catch (EstateException e) {
            return ResponseResult.<List<User>>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<List<User>>builder()
                .data(userList)
                .message("Getting user list is successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for user list.
     *
     * @return Return the result.
     */
    @GetMapping("/view")
    @PreAuthorize("hasAuthority('user:view')")
    public ResponseResult<User> viewCurrentUser() {
        User user;
        try {
            user = userService.viewCurrentUser();
        } catch (EstateException e) {
            return ResponseResult.<User>builder()
                    .data(null)
                    .message(e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        return ResponseResult.<User>builder()
                .data(user)
                .message("View user information successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
