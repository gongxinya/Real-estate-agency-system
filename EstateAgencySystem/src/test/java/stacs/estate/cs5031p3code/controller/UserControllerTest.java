package stacs.estate.cs5031p3code.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The class for testing UserController
 *
 * @author 220017897
 * @version 0.0.1
 */
@SpringBootTest
public class UserControllerTest {

    /**
     * The UserController instance to test.
     */
    @InjectMocks
    private UserController userController;

    /**
     * The mocked UserService instance.
     */
    @Mock
    private UserService userService;

    /**
     * The user instance used in the tests.
     */
    private User user;

    /**
     * Sets up the test instance.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .userEmail("testUser")
                .userPassword("testPassword")
                .build();
    }

    /**
     * Tests the {@link UserController#login(User)} method with a successful login.
     */
    @Test
    void testLoginSuccessful() throws EstateException {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("user_key", "testUserKey");
        when(userService.login(any(User.class))).thenReturn(tokenMap);
        var response = userController.login(user);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("Login successful!", response.getMessage());
        assertEquals(tokenMap, response.getData());
    }

    /**
     * Tests the {@link UserController#login(User)} method with a failed login.
     */
    @Test
    void testLoginFailed() throws EstateException {
        String errorMessage = "Unauthorized";
        doThrow(new EstateException(HttpStatus.UNAUTHORIZED.getReasonPhrase())).when(userService).login(any(User.class));
        var response = userController.login(user);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
        assertEquals(errorMessage, response.getMessage());
        assertNull(response.getData());
    }

    /**
     * Tests the {@link UserController#logout()} method with a successful logout.
     */
    @Test
    void testLogoutSuccessful() throws EstateException {
        var response = userController.logout();
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("Logout successful!", response.getMessage());
        assertNull(response.getData());
    }

    /**
     * Tests the {@link UserController#logout()} method with a failed logout.
     */
    @Test
    void testLogoutFailed() throws EstateException {
        String errorMessage = "Unauthorized";
        doThrow(new EstateException(HttpStatus.UNAUTHORIZED.getReasonPhrase())).when(userService).logout();
        var response = userController.logout();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
        assertEquals(errorMessage, response.getMessage());
        assertNull(response.getData());
    }

    /**
     * Tests the {@link UserController#register(User)} method with a successful registration.
     */
    @Test
    void testRegisterSuccessful() throws EstateException {
        var response = userController.register(user);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNull(response.getData());
        assertEquals("Create successful!", response.getMessage());
        verify(userService, times(1)).register(user);
    }

    /**
     * Tests the {@link UserController#register(User)} method with a failed registration.
     */
    @Test
    void testRegisterFailure() throws EstateException {
        var exsistUser = User.builder()
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .build();
        // Attempt to register the user
        doThrow(new EstateException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())).when(userService).register(exsistUser);
        var response = userController.register(exsistUser);
        var errorMessage = "Internal Server Error";
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        assertEquals(errorMessage, response.getMessage());
        assertNull(response.getData());

    }

    /**
     * Tests the  method with failed login due to incorrect password.
     */
    @Test
    void testLoginIncorrectPassword() throws EstateException {
        var wrongUser = User.builder()
                .userEmail(user.getUserEmail())
                .userPassword("wrongPassword")
                .build();
        when(userService.login(wrongUser)).thenThrow(new EstateException("Incorrect password"));
        var response = userController.login(wrongUser);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
        assertNull(response.getData());
        assertEquals("Incorrect password", response.getMessage());
        verify(userService, times(1)).login(wrongUser);
    }

    /**
     * Tests the  method with failed login due to non-existent email.
     */
    @Test
    void testLoginNonExistentEmail() throws EstateException {
        var wrongUser = User.builder()
                .userEmail("noneEmail")
                .userPassword(user.getUserPassword())
                .build();
        when(userService.login(wrongUser)).thenThrow(new EstateException("Email does not exist"));

        var response = userController.login(wrongUser);

        assertNull(response.getData());
        assertEquals("Email does not exist", response.getMessage());
        verify(userService, times(1)).login(wrongUser);
    }
}
