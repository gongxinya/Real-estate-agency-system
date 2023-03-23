package stacs.estate.cs5031p3code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stacs.estate.cs5031p3code.mapper.PermissionMapper;
import stacs.estate.cs5031p3code.mapper.UserMapper;
import stacs.estate.cs5031p3code.model.dto.SecurityUser;
import stacs.estate.cs5031p3code.model.po.User;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        var queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserEmail, userEmail);
        var user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("Email or password is error!");
        }

        var list = permissionMapper.selectPermisssionsByUserId(user.getUserId());
        //把数据封装成UserDetails返回
        return new SecurityUser(user, list);
    }
}
