package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.User;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing UserService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
@Transactional
public class UserServiceTest {

    /**
     * The user service.
     */
    @Autowired
    private UserService userService;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The method for testing login.
     */
    @Test
    public void testLogin() {
        var user = User.builder().userEmail("hz65@st-andrews.ac.uk").userPassword("1234").build();
        assertDoesNotThrow(() -> {
            var map = userService.login(user);
            assertNotNull(map);
            assertNotNull(map.get("user_key"));
        });
    }

    /**
     * The method for testing of logout.
     */
    @Test
    public void testRegister() {
        // 1. User properties are null.
        var user1 = User.builder().userEmail("").userName("").userPassword(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            userService.register(user1);
        });
        var expectedMessage = "User email, password and name cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. The user email is existed.
        var user2 = User.builder().userEmail("hz65@st-andrews.ac.uk").userName("Henry").userPassword(passwordEncoder.encode("1234")).build();
        exception = assertThrows(EstateException.class, () -> {
            userService.register(user2);
        });
        expectedMessage = "User email is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Register successful!
        var user3 = User.builder().userEmail("jw385@st-andrews.ac.uk").userName("Jiaxin").userPassword(passwordEncoder.encode("1234")).build();
        assertDoesNotThrow(() -> {
            userService.register(user3);
        });
    }

    /**
     * The method for testing delete user by user id.
     */
    @Test
    public void testDeleteUserById() {
        // 1. user id is null
        Long userId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            userService.deleteUserById(userId1);
        });
        var expectedMessage = "User id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. User is not existed
        Long userId2 = 5L;
        exception = assertThrows(EstateException.class, () -> {
            userService.deleteUserById(userId2);
        });
        expectedMessage = "User is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. User has flats in there, cannot remove
        Long userId3 = 3L;
        exception = assertThrows(EstateException.class, () -> {
            userService.deleteUserById(userId3);
        });
        expectedMessage = "User has flats in there, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Delete successful!
        Long userId4 = 2L;
        assertDoesNotThrow(() -> {
            userService.deleteUserById(userId4);
        });
    }

    /**
     * The method for updating user by user id.
     */
    @Test
    public void testUpdateUserByUserId() {
        // 1. User or user id is empty
        var user1 = User.builder().userId(null).build();
        var exception = assertThrows(EstateException.class, () -> {
            userService.updateUserByUserId(user1.getUserId(), user1);
        });
        var expectedMessage = "User or user id is empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. User email is existed!
        var user2 = User.builder().userId(2L).userEmail("hz65@st-andrews.ac.uk").build();
        exception = assertThrows(EstateException.class, () -> {
            userService.updateUserByUserId(user2.getUserId(), user2);
        });
        expectedMessage = "User email is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. User email can not be empty.
        var user3 = User.builder().userId(2L).userEmail("").build();
        exception = assertThrows(EstateException.class, () -> {
            userService.updateUserByUserId(user3.getUserId(), user3);
        });
        expectedMessage = "User email cannot be empty!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Update successful!
        var user4 = User.builder().userId(1L).userEmail("hz65@st-andrews.ac.uk").userPhone("07536").build();
        assertDoesNotThrow(() -> {
            userService.updateUserByUserId(user4.getUserId(), user4);
        });
    }

    /**
     * The method for testing getting user list.
     */
    @Test
    public void testUserList() {
        assertDoesNotThrow(() -> {
            var userList = userService.getUserList();
            assertEquals(3, userList.size());
        });
    }

    /**
     * The method for testing password encoder.
     */
    @Test
    public void TestBCryptPasswordEncoder() {
        String encode = passwordEncoder.encode("1234");
//        System.out.println(encode);
        assertTrue(passwordEncoder.matches("1234", encode));
    }
}
