package stacs.estate.cs5031p3code.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import stacs.estate.cs5031p3code.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing UserService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
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
     * The method for testing getting user list.
     */
    @Test
    public void testUserList() {
        var userList = userService.getUserList();
        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    /**
     * The method for testing password encoder.
     */
    @Test
    public void TestBCryptPasswordEncoder() {
        System.out.println(passwordEncoder.
                matches("1234",
                        "$2a$10$0K/.pvvqamQlUjy69y9yqOJz/u7qpznaDd1bxFm6ZT1vrfr3h02Ky"));
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }
}
