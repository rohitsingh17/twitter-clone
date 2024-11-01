package com.rohit.controller;

import com.rohit.exception.InvalidInputException;
import com.rohit.exception.UserNotFoundException;
import com.rohit.model.User;
import com.rohit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void followUser_success() throws Exception {
        String username1 = "username1";
        String username2 = "username2";
        User user = User.builder()
                .userId(1L)
                .username(username1)
                .build();
        User follower = User.builder()
                .userId(2L)
                .username(username2)
                .build();

        Mockito.when(userService.getUserByUsername(username1)).thenReturn(user);
        Mockito.when(userService.getUserByUsername(username2)).thenReturn(follower);

        mockMvc.perform(post("/api/users/"+username1+"/follow/"+username2))
                .andExpect(status().isOk())
                .andExpect(content().string("User followed successfully"));
    }

    @Test
    public void followUser_userNotFound() throws Exception {
        String username1 = "username1";
        String username2 = "username2";
        Mockito.when(userService.getUserByUsername(username1)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(post("/api/users/"+username1+"/follow/"+username2))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    public void unfollowUser_invalidInput() throws Exception {
        String username1 = "username1";
        String username2 = "username2";
        User user = User.builder()
                .userId(1L)
                .username(username1)
                .build();
        User follower = User.builder()
                .userId(2L)
                .username(username2)
                .build();
        Mockito.when(userService.getUserByUsername(username1)).thenReturn(user);
        Mockito.when(userService.getUserByUsername(username2)).thenReturn(follower);
        doThrow(new InvalidInputException("Invalid input")).when(userService).unfollowUser(user, follower);

        mockMvc.perform(post("/api/users/"+username1+"/unfollow/"+username2))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidInputException));
    }

}
