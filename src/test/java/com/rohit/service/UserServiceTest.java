package com.rohit.service;

import com.rohit.exception.UserNotFoundException;
import com.rohit.model.User;
import com.rohit.model.UserFollower;
import com.rohit.repository.UserFollowerRepository;
import com.rohit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFollowerRepository userFollowerRepository;

    @Test(expected = UserNotFoundException.class)
    public void getUserByUsername_userNotFound() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        userService.getUserByUsername(username);
    }

    @Test
    public void getUserByUsername_userFound() {
        Long userId = 1L;
        String username = "username";
        User user = User.builder()
                .userId(userId)
                .username(username)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        User result = userService.getUserByUsername(username);
        assertEquals(userId, result.getUserId());
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void followUser_success() {
        User user = User.builder()
                .userId(1L)
                .username("username1")
                .build();
        User follower = User.builder()
                .userId(2L)
                .username("username2")
                .build();

        userService.followUser(user, follower);
        verify(userFollowerRepository).save(any(UserFollower.class));
    }
}
