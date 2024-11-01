package com.rohit.dto;

import com.rohit.model.Tweet;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TweetDto {
    private final Long tweetId;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final int likes;
    private final int retweetCount;

    public static TweetDto ofEntity(Tweet tweet){
        return TweetDto.builder()
                .tweetId(tweet.getTweetId())
                .username(tweet.getUser().getUsername())
                .content(tweet.getContent())
                .createdAt(tweet.getCreatedAt())
                .likes(tweet.getLikes())
                .retweetCount(tweet.getRetweet())
                .build();
    }
}
