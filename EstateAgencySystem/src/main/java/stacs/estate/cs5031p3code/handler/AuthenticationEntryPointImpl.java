package stacs.estate.cs5031p3code.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import stacs.estate.cs5031p3code.utils.ResponseResult;
import stacs.estate.cs5031p3code.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //处理异常
        WebUtils.renderString(response, JSON.toJSONString(
                ResponseResult.<Void>builder()
                        .data(null)
                        .message("Authentication Failed!")
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build()
        ));
    }
}
