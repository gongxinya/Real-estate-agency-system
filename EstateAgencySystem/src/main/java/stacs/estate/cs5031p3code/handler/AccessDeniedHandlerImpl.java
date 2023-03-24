package stacs.estate.cs5031p3code.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import stacs.estate.cs5031p3code.utils.ResponseResult;
import stacs.estate.cs5031p3code.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A handler for handling the exception about Authorization.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    /**
     * Override the handle method.
     *
     * @param request               Http request.
     * @param response              Http response.
     * @param accessDeniedException The accessDeniedException object.
     * @throws IOException      The IOException object.
     * @throws ServletException The ServletException object.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Handle exception.
        WebUtils.renderString(response, JSON.toJSONString(
                ResponseResult.<Void>builder()
                        .data(null)
                        .message("Permission Denied!")
                        .code(HttpStatus.FORBIDDEN.value())
                        .build()
        ));
    }
}
