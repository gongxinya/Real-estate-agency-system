package stacs.estate.cs5031p3code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.estate.cs5031p3code.utils.ResponseResult;

/**
 * @author 220032952
 * @version 0.0.1
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseResult<Void> test() {
        return ResponseResult.<Void>builder()
                .data(null)
                .message("Test successful!")
                .code(HttpStatus.OK.value())
                .build();
    }
}
