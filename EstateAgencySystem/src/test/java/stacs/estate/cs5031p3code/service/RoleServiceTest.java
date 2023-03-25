package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Role;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing RoleService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
public class RoleServiceTest {

    /**
     * The role service.
     */
    @Autowired
    private RoleService roleService;

    /**
     * The method for testing createRole.
     */
    @Test
    public void testCreateRole() {
        // 1. Properties are null
        var role1 = Role.builder().roleName(null).roleKey(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            roleService.createRole(role1);
        });
        var expectedMessage = "Role name or key cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Role name is existed
        var role2 = Role.builder().roleName("Guest").roleKey("Guest").build();
        exception = assertThrows(EstateException.class, () -> {
            roleService.createRole(role2);
        });
        expectedMessage = "Role name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Creating role successful
        var role3 = Role.builder().roleName("Agency").roleKey("Agency").build();
        assertDoesNotThrow(() -> {
            roleService.createRole(role3);
        });
    }

    /**
     * The method for testing deleteRoleByRoleId.
     */
    @Test
    public void testDeleteRoleByRoleId() {
        // 1. role id is null
        Long roleId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            roleService.deleteRoleByRoleId(roleId1);
        });
        var expectedMessage = "Role id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. role is not existed
        Long roleId2 = 4L;
        exception = assertThrows(EstateException.class, () -> {
            roleService.deleteRoleByRoleId(roleId2);
        });
        expectedMessage = "Role is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. The connection between user and role.
        Long roleId3 = 2L;
        exception = assertThrows(EstateException.class, () -> {
            roleService.deleteRoleByRoleId(roleId3);
        });
        expectedMessage = "Role has relationship with users, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. The connection between role and permission.
        Long roleId4 = 2L;
        exception = assertThrows(EstateException.class, () -> {
            roleService.deleteRoleByRoleId(roleId4);
        });
        expectedMessage = "Role has relationship with permissions, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Delete successful
        Long roleId5 = 4L;
        assertDoesNotThrow(() -> {
            roleService.deleteRoleByRoleId(roleId5);
        });
    }

    /**
     * The method for testing updateRoleByRoleId.
     */
    @Test
    public void testUpdateRoleByRoleId() {
        // 1. Role or role id is empty
        var role1 = Role.builder().roleId(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            roleService.updateRoleByRoleId(role1.getRoleId(), role1);
        });
        var expectedMessage = "Role or role id is empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Role name is existed!
        var role2 = Role.builder().roleId(2L).roleName("Admin").build();
        exception = assertThrows(EstateException.class, () -> {
            roleService.updateRoleByRoleId(role2.getRoleId(), role2);
        });
        expectedMessage = "Role name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Role name can not be empty.
        var role3 = Role.builder().roleId(2L).roleName("").build();
        exception = assertThrows(EstateException.class, () -> {
            roleService.updateRoleByRoleId(role3.getRoleId(), role3);
        });
        expectedMessage = "Role name cannot be empty!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Update successful!
        var role4 = Role.builder().roleId(2L).roleName("Master").roleKey("Master").build();
        assertDoesNotThrow(() -> {
            roleService.updateRoleByRoleId(role4.getRoleId(), role4);
        });
    }

    /**
     * The method for testing getRoleList.
     */
    @Test
    public void testGetRoleList() {
        assertDoesNotThrow(() -> {
            var roleList = roleService.getRoleList();
            assertEquals(3, roleList.size());
        });
    }
}
