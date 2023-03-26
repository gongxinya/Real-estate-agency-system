package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.UserRole;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing UserRoleService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
public class UserRoleServiceTest {

    /**
     * The user role service.
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * The method for testing assign role to user.
     */
    @Test
    public void testCreateRoleToUser() {
        // 1. user id or role id is null.
        var userRole1 = UserRole.builder()
                .userId(null)
                .roleId(1L)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            userRoleService.createRoleToUser(userRole1.getUserId(), userRole1.getRoleId());
        });
        var expectedMessage = "The id of user or role cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. user id is not existed.
        var userRole2 = UserRole.builder()
                .userId(4L)
                .roleId(3L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.createRoleToUser(userRole2.getUserId(), userRole2.getRoleId());
        });
        expectedMessage = "User is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. role id is not existed.
        var userRole3 = UserRole.builder()
                .userId(3L)
                .roleId(4L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.createRoleToUser(userRole3.getUserId(), userRole3.getRoleId());
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. The relationship is existed.
        var userRole4 = UserRole.builder()
                .userId(1L)
                .roleId(1L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.createRoleToUser(userRole4.getUserId(), userRole4.getRoleId());
        });
        expectedMessage = "The relationship of user and role is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Creating relationship successful
        var userRole5 = UserRole.builder()
                .userId(3L)
                .roleId(2L)
                .build();
        assertDoesNotThrow(() -> {
            userRoleService.createRoleToUser(userRole5.getUserId(), userRole5.getRoleId());
        });
    }

    /**
     * The method for testing removeRoleToUser.
     */
    @Test
    public void testRemoveRoleToUser() {
        // 1. user id or role id is null.
        var userRole1 = UserRole.builder()
                .userId(null)
                .roleId(1L)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            userRoleService.removeRoleToUser(userRole1.getUserId(), userRole1.getRoleId());
        });
        var expectedMessage = "The id of user or role cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. user id is not existed.
        var userRole2 = UserRole.builder()
                .userId(4L)
                .roleId(3L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.removeRoleToUser(userRole2.getUserId(), userRole2.getRoleId());
        });
        expectedMessage = "User is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. role id is not existed.
        var userRole3 = UserRole.builder()
                .userId(3L)
                .roleId(4L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.removeRoleToUser(userRole3.getUserId(), userRole3.getRoleId());
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. The relationship is not existed.
        var userRole4 = UserRole.builder()
                .userId(3L)
                .roleId(1L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.removeRoleToUser(userRole4.getUserId(), userRole4.getRoleId());
        });
        expectedMessage = "Remove role to user failed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Creating relationship successful
        var userRole5 = UserRole.builder()
                .userId(3L)
                .roleId(2L)
                .build();
        assertDoesNotThrow(() -> {
            userRoleService.removeRoleToUser(userRole5.getUserId(), userRole5.getRoleId());
        });
    }

    /**
     * The method for testing listRolesByUserId.
     */
    @Test
    public void testListRolesByUserId() {
        // 1. user id is null.
        var exception = assertThrows(EstateException.class, () -> {
            userRoleService.listRolesByUserId(null);
        });
        var expectedMessage = "The id of user cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. user is not existed.
        exception = assertThrows(EstateException.class, () -> {
            userRoleService.listRolesByUserId(4L);
        });
        expectedMessage = "User is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Get role list successful
        assertDoesNotThrow(() -> {
            userRoleService.listRolesByUserId(1L);
        });
    }
}
