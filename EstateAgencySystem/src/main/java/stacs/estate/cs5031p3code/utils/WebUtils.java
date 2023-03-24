package stacs.estate.cs5031p3code.utils;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A class for web application.
 *
 * @author 220032952
 * @version 0.0.1
 */
public class WebUtils {

    /**
     * Rendering strings to the client.
     *
     * @param response Rendering object.
     * @param string   Rendering string.
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}