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

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //处理异常
        WebUtils.renderString(response, JSON.toJSONString(
                ResponseResult.<Void>builder()
                        .data(null)
                        .message("Permission Denied!")
                        .code(HttpStatus.FORBIDDEN.value())
                        .build()
        ));
    }
}
