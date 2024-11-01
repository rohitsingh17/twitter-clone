package com.rohit.service;

import com.rohit.exception.NoTimelineDataException;
import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.model.UserFollower;
import com.rohit.repository.TweetRepository;
import com.rohit.repository.UserFollowerRepository;
import com.rohit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimelineServiceTest {
    @InjectMocks
    private TimelineService timelineService;

    @Mock
    private UserFollowerRepository userFollowerRepository;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void getUserTimeline_noTimeline() {
        String username = "user1";
        User user = User.builder()
                .userId(1L)
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userFollowerRepository.findByFollower(any())).thenReturn(new ArrayList<>());
        assertThrows(NoTimelineDataException.class, () -> timelineService.getUserTimelineByUsername(username));
    }

    @Test
    public void getUserTimeline_success() {
        String username = "user1";
        User user1 = User.builder()
                .userId(1L)
                .username("user1")
                .build();
        User user2 = User.builder()
                .userId(2L)
                .username("user2")
                .build();
        User user3 = User.builder()
                .userId(3L)
                .username("user3")
                .build();
        List<Long> followingIds = Arrays.asList(2L, 3L);
        List<Tweet> tweets = Arrays.asList(
                Tweet.builder().tweetId(1L).user(user2).content("Tweet from user 2").build(),
                Tweet.builder().tweetId(2L).user(user3).content("Tweet from user 3").build()
        );
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(userFollowerRepository.findByFollower(any())).thenReturn(
                followingIds.stream().map(id -> new UserFollower(User.builder().userId(id).build(), user1))
                        .collect(Collectors.toList())
        );
        when(tweetRepository.findByUser_UserIdInOrderByCreatedAtDesc(followingIds)).thenReturn(tweets);

        List<Tweet> result = timelineService.getUserTimelineByUsername(username);

        assertEquals(2, result.size());
        verify(tweetRepository).findByUser_UserIdInOrderByCreatedAtDesc(followingIds);
    }
}
