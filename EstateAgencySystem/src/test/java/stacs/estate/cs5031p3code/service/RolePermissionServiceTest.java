package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.RolePermission;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing RolePermissionService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
public class RolePermissionServiceTest {

    /**
     * The role permission service.
     */
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * The method for testing createPermissionToRole.
     */
    @Test
    public void testCreatePermissionToRole() {
        // 1. role id or permission id is null.
        var rolePermission1 = RolePermission.builder()
                .roleId(null)
                .permissionId(1L)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.createPermissionToRole(rolePermission1.getRoleId(), rolePermission1.getPermissionId());
        });
        var expectedMessage = "The id of role or permission cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. role id is not existed.
        var rolePermission2 = RolePermission.builder()
                .roleId(4L)
                .permissionId(1L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.createPermissionToRole(rolePermission2.getRoleId(), rolePermission2.getPermissionId());
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. permission id is not existed.
        var rolePermission3 = RolePermission.builder()
                .roleId(3L)
                .permissionId(29L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.createPermissionToRole(rolePermission3.getRoleId(), rolePermission3.getPermissionId());
        });
        expectedMessage = "Permission is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. The relationship is existed.
        var rolePermission4 = RolePermission.builder()
                .roleId(1L)
                .permissionId(1L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.createPermissionToRole(rolePermission4.getRoleId(), rolePermission4.getPermissionId());
        });
        expectedMessage = "The relationship of role and permission is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Creating relationship successful.
        var rolePermission5 = RolePermission.builder()
                .roleId(2L)
                .permissionId(28L)
                .build();
        assertDoesNotThrow(() -> {
            rolePermissionService.createPermissionToRole(rolePermission5.getRoleId(), rolePermission5.getPermissionId());
        });
    }

    /**
     * The method for testing removePermissionToRole.
     */
    @Test
    public void testRemovePermissionToRole() {
        // 1. role id or permission id is null.
        var rolePermission1 = RolePermission.builder()
                .roleId(null)
                .permissionId(1L)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.removePermissionToRole(rolePermission1.getRoleId(), rolePermission1.getPermissionId());
        });
        var expectedMessage = "The id of role or permission cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. role id is not existed.
        var rolePermission2 = RolePermission.builder()
                .roleId(4L)
                .permissionId(1L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.removePermissionToRole(rolePermission2.getRoleId(), rolePermission2.getPermissionId());
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. permission id is not existed.
        var rolePermission3 = RolePermission.builder()
                .roleId(3L)
                .permissionId(29L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.removePermissionToRole(rolePermission3.getRoleId(), rolePermission3.getPermissionId());
        });
        expectedMessage = "Permission is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. The relationship is not existed.
        var rolePermission4 = RolePermission.builder()
                .roleId(3L)
                .permissionId(28L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.removePermissionToRole(rolePermission4.getRoleId(), rolePermission4.getPermissionId());
        });
        expectedMessage = "Remove permission to role failed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Creating relationship successful.
        var rolePermission5 = RolePermission.builder()
                .roleId(2L)
                .permissionId(28L)
                .build();
        assertDoesNotThrow(() -> {
            rolePermissionService.removePermissionToRole(rolePermission5.getRoleId(), rolePermission5.getPermissionId());
        });
    }

    /**
     * The method for testing listPermissionsByRoleId.
     */
    @Test
    public void testListPermissionsByRoleId() {
        // 1. role id is null.
        var exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.listPermissionsByRoleId(null);
        });
        var expectedMessage = "The id of role cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. role is not existed.
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.listPermissionsByRoleId(4L);
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Get role list successful
        assertDoesNotThrow(() -> {
            rolePermissionService.listPermissionsByRoleId(1L);
        });
    }

    /**
     * The method for testing listPermissionsByUserId.
     */
    @Test
    public void testListPermissionsByUserId() {
        // 1. user id is null.
        var exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.listPermissionsByUserId(null);
        });
        var expectedMessage = "The id of user cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. user is not existed.
        exception = assertThrows(EstateException.class, () -> {
            rolePermissionService.listPermissionsByUserId(4L);
        });
        expectedMessage = "User is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Get role list successful
        assertDoesNotThrow(() -> {
            rolePermissionService.listPermissionsByUserId(1L);
        });
    }
}
