package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
        if (Objects.isNull(user) || !StringUtils.hasText(user.getUserEmail()) || !StringUtils.hasText(user.getUserPassword())) {
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
     * The method for creating a new user.
     *
     * @return Return result.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseResult<Void> createUser() {
        // TODO Complete the logic.

        return ResponseResult.<Void>builder()
                .data(null)
                .message("Create successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * The method for user list.
     *
     * @return Return the result.
     */
    @GetMapping("/view/{userId}}")
    @PreAuthorize("hasAuthority('user:view')")
    public ResponseResult<Void> viewUser(@PathVariable String userId) {
        // TODO Complete the logic.
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Read successful!")
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
        var userList = userService.getUserList();
        if (userList == null || userList.size() == 0) {
            return ResponseResult.<List<User>>builder()
                    .data(null)
                    .message("User list is empty!")
                    .code(HttpStatus.OK.value())
                    .build();
        }
        return ResponseResult.<List<User>>builder()
                .data(userList)
                .message("Getting user list is successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

}
