package com.nhnacademy.jdbc.board.user.service;

import com.nhnacademy.jdbc.board.user.domain.User;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String id,String password);

    Optional<User> getUserByNo(int userNo);
}
