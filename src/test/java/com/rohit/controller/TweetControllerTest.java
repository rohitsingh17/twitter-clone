package com.rohit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohit.dto.TweetRequest;
import com.rohit.exception.UserNotFoundException;
import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.service.TweetService;
import com.rohit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetController.class)
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TweetService tweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createTweet_success() throws Exception {
        String username1 = "username1";
        TweetRequest tweetRequest = new TweetRequest("This is a sample tweet content.");
        User user = User.builder()
                .userId(1L)
                .username(username1)
                .build();
        Tweet tweet = Tweet.builder()
                .user(user)
                .content("This is a sample tweet content.")
                .build();

        Mockito.when(userService.getUserByUsername(username1)).thenReturn(user);
        Mockito.when(tweetService.createTweet(user, "This is a sample tweet content.")).thenReturn(tweet);

        mockMvc.perform(post("/api/tweets/"+username1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tweetRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(tweetRequest.getContent()));
    }

    @Test
    public void createTweet_userNotFound() throws Exception {
        String username1 = "username1";
        TweetRequest tweetRequest = new TweetRequest("This is a sample tweet content.");
        Mockito.when(userService.getUserByUsername(username1)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(post("/api/tweets/"+username1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tweetRequest)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    public void createTweet_invalidInput() throws Exception {
        TweetRequest tweetRequest = new TweetRequest("");
        mockMvc.perform(post("/api/tweets/"+userService)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tweetRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content").value("Tweet content cannot be empty"));
    }
}
