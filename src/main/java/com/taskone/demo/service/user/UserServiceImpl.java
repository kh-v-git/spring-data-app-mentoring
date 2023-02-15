package com.taskone.demo.service.user;

import com.taskone.demo.domain.User;
import com.taskone.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(final long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("User not found with Id = " + userId));
    }

    @Override
    public User getUserByEmail(final String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new NoSuchElementException("User not found with Email = " + email));
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return userRepository.findUsersByName(name);
    }

    @Override
    public User createUser(final User user) {
        User savedUser = userRepository.save(user);

        log.debug("New User with ID {} was created.", savedUser.getId());

        return savedUser;
    }

    @Override
    public User updateUser(final User user) {
        User savedUser = userRepository.save(user);

        log.debug("User with ID {} was updated.", savedUser.getId());
        return savedUser;
    }

    @Override
    public boolean deleteUser(final long userId) {
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            log.debug("User with ID {} was NOT deleted cause NOT found.", userId);

            return false;
        }
        userRepository.delete(maybeUser.get());

        log.debug("User with ID {} was deleted.", userId);

        return true;
    }
}
