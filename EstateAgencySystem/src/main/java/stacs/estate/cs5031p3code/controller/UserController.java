package stacs.estate.cs5031p3code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.service.UserService;
import stacs.estate.cs5031p3code.utils.ResponseResult;

import java.util.Map;

/**
 * @author 220032952
 * @version 0.0.1
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<Map<String, String>> login(@RequestBody User user) {
        return ResponseResult.<Map<String, String>>builder()
                .data(userService.login(user))
                .message("Login successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/logout")
    public ResponseResult<Void> logout() {
        this.userService.logout();
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Logout successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseResult<Void> createUser() {
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Create successful!")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/read")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseResult<Void> readUser() {
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Read successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
