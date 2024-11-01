package com.rohit.service;

import com.rohit.exception.NoTimelineDataException;
import com.rohit.exception.UserNotFoundException;
import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.repository.TweetRepository;
import com.rohit.repository.UserFollowerRepository;
import com.rohit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimelineService {

    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Tweet> getUserTimelineByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        List<Long> followingIds = userFollowerRepository.findByFollower(user)
                .stream().map(f -> f.getUser().getUserId())
                .collect(Collectors.toList());

        List<Tweet> tweets = tweetRepository.findByUser_UserIdInOrderByCreatedAtDesc(followingIds);
        if (tweets.isEmpty()) {
            throw new NoTimelineDataException("No tweets available for the following users.");
        }

        return tweets;
    }
}
