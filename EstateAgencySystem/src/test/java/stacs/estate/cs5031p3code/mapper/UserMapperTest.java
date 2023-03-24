package stacs.estate.cs5031p3code.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import stacs.estate.cs5031p3code.model.po.User;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUser(){
        var queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserEmail, "hz65@st-andrews.ac.uk");
        var user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void TestBCryptPasswordEncoder(){

        System.out.println(passwordEncoder.
                matches("1234",
                        "$2a$10$0K/.pvvqamQlUjy69y9yqOJz/u7qpznaDd1bxFm6ZT1vrfr3h02Ky"));
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }
}
