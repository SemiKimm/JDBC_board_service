package com.nhnacademy.jdbc.board.user.service.impl;

import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.mapper.UserMapper;
import com.nhnacademy.jdbc.board.user.service.UserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    private final UserMapper userMapper;

    public DefaultUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> getUser(String id, String password) {
        return userMapper.getUser(id,password);
    }

    @Override
    public Optional<User> getUserByNo(int userNo) {
        return userMapper.getUserByNo(userNo);
    }
}
