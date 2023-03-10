package com.taskone.demo.service.user;

import com.taskone.demo.domain.User;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.utils.exception.BookingServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(final long userId) throws BookingServiceException {
        return userRepository.findById(userId).orElseThrow(() -> new BookingServiceException(String.format("User not found with Id: %s.", userId)));
    }

    @Override
    public User getUserByEmail(final String email) throws BookingServiceException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new BookingServiceException(String.format("User not found with Email: %s.", email)));
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return userRepository.findUsersByName(name, pageable);
    }

    @Override
    @Transactional
    public User createUser(final User user) {
        User savedUser = userRepository.save(user);

        log.debug("New User with ID {} was created.", savedUser.getId());

        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(final User user) {
        User savedUser = userRepository.save(user);

        log.debug("User with ID {} was updated.", savedUser.getId());
        return savedUser;
    }

    @Override
    @Transactional
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
