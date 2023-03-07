package com.taskone.demo.service.user;

import com.taskone.demo.domain.User;
import com.taskone.demo.utils.exception.BookingServiceException;

import java.util.List;

public interface UserService {
    User getUserById(long userId) throws BookingServiceException;

    User getUserByEmail(String email) throws BookingServiceException;

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(long userId);
}
