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

/**
 * A handler for handling the exception about Authentication.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * Override the commence method.
     *
     * @param request       Http request.
     * @param response      Http response.
     * @param authException The authException object.
     * @throws IOException      The IOException object.
     * @throws ServletException The ServletException object.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Handle exception.
        WebUtils.renderString(response, JSON.toJSONString(
                ResponseResult.<Void>builder()
                        .data(null)
                        .message(authException.getMessage())
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build()
        ));
    }
}
