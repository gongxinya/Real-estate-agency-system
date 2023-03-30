package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Permission;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing PermissionService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
@Transactional
public class PermissionServiceTest {

    /**
     * The permission service.
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * The method for testing createPermission.
     */
    @Test
    public void testCreatePermission() {
        // 1. Properties are null
        var permission1 = Permission.builder().permissionName(null).permissionKey(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            permissionService.createPermission(permission1);
        });
        var expectedMessage = "Permission name or key cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Permission name is existed
        var permission2 = Permission.builder().permissionName("Delete user").permissionKey("user:delete").build();
        exception = assertThrows(EstateException.class, () -> {
            permissionService.createPermission(permission2);
        });
        expectedMessage = "Permission name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Creating permission successful
        var permission3 = Permission.builder().permissionName("Test user").permissionKey("user:test").build();
        assertDoesNotThrow(() -> {
            permissionService.createPermission(permission3);
        });
    }

    /**
     * The method for testing deletePermissionByPermissionId.
     */
    @Test
    public void testDeletePermissionByPermissionId() {
        // 1. permission id is null
        Long permissionId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            permissionService.deletePermissionByPermissionId(permissionId1);
        });
        var expectedMessage = "Permission id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. permission is not existed
        Long permissionId2 = 30L;
        exception = assertThrows(EstateException.class, () -> {
            permissionService.deletePermissionByPermissionId(permissionId2);
        });
        expectedMessage = "Permission is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. The connection between user and permission.
        Long permissionId3 = 2L;
        exception = assertThrows(EstateException.class, () -> {
            permissionService.deletePermissionByPermissionId(permissionId3);
        });
        expectedMessage = "Permission has relationship with roles, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * The method for testing updatePermissionByPermissionId.
     */
    @Test
    public void testUpdatePermissionByPermissionId() {
        // 1. Permission or permission id is empty
        var permission1 = Permission.builder().permissionId(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            permissionService.updatePermissionByPermissionId(permission1.getPermissionId(), permission1);
        });
        var expectedMessage = "Permission or permission id is empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Permission name is existed!
        var permission2 = Permission.builder().permissionId(2L).permissionName("Delete user").build();
        exception = assertThrows(EstateException.class, () -> {
            permissionService.updatePermissionByPermissionId(permission2.getPermissionId(), permission2);
        });
        expectedMessage = "Permission name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Permission name can not be empty.
        var permission3 = Permission.builder().permissionId(2L).permissionName("").build();
        exception = assertThrows(EstateException.class, () -> {
            permissionService.updatePermissionByPermissionId(permission3.getPermissionId(), permission3);
        });
        expectedMessage = "Permission name cannot be empty!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Update successful!
        var permission4 = Permission.builder().permissionId(28L).permissionName("Test user").permissionKey("user:test").build();
        assertDoesNotThrow(() -> {
            permissionService.updatePermissionByPermissionId(permission4.getPermissionId(), permission4);
        });
    }

    /**
     * The method for testing getPermissionList.
     */
    @Test
    public void testGetPermissionList() {
        assertDoesNotThrow(() -> {
            var permissionList = permissionService.getPermissionList();
            assertEquals(28, permissionList.size());
        });
    }
}
