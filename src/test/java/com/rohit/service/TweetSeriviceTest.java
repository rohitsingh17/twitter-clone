package com.rohit.service;

import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.repository.TweetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TweetSeriviceTest {
    @InjectMocks
    private TweetService tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Test
    public void createTweet_success() {
        User user = User.builder()
                .userId(1L)
                .username("username1")
                .build();
        Tweet tweet = Tweet.builder()
                .user(user)
                .content("Hello World!")
                .build();

        Mockito.when(tweetRepository.save(Mockito.any(Tweet.class))).thenReturn(tweet);

        Tweet createdTweet = tweetService.createTweet(user, "Hello World!");

        assertEquals("Hello World!", createdTweet.getContent());
    }
}
