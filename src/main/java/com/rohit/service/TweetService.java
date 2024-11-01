package com.rohit.service;

import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Tweet createTweet(User user, String content){
        Tweet tweet = Tweet.builder()
                .user(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return tweetRepository.save(tweet);
    }
}
