package com.taskone.demo.service.user;


import com.google.common.collect.Lists;
import com.taskone.demo.domain.User;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final long USER_ID = 20L;
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 32;
    private static final String USER_EMAIL = "jhon_macklein@die.hard";
    private static final String USER_NAME = "John Macklein";

    @Mock
    private User user;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl testingInstance;

    @Test
    void shouldGetUserById() throws  Exception {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(USER_ID);

        User result = testingInstance.getUserById(USER_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(USER_ID));
    }

    @Test
    void shouldGetUserByEmail() throws  Exception {
        when(userRepository.findUserByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(USER_EMAIL);

        User result = testingInstance.getUserByEmail(USER_EMAIL);

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldGetUsersByName() throws  Exception {
        List<User> userList = Lists.newArrayList(user);
        when(user.getName()).thenReturn(USER_NAME);
        when(userRepository.findUsersByName(USER_NAME)).thenReturn(userList);

        List<User> result = testingInstance.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is(USER_NAME));
    }

    @Test
    void shouldCreateUser() throws  Exception {
        when(userRepository.save(user)).thenReturn(user);
        when(user.getName()).thenReturn(USER_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        User result = testingInstance.createUser(user);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldUpdateUser() throws  Exception {
        when(userRepository.save(user)).thenReturn(user);
        when(user.getId()).thenReturn(USER_ID);
        when(user.getName()).thenReturn(USER_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        User result = testingInstance.updateUser(user);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(USER_ID));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldDeleteUser() throws  Exception {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        boolean result = testingInstance.deleteUser(USER_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.TRUE));
    }

    @Test
    void shouldNotDeleteUser() throws  Exception {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        boolean result = testingInstance.deleteUser(USER_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.FALSE));
    }
}
