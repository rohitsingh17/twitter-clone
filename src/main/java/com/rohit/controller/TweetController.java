package com.rohit.controller;

import com.rohit.dto.TweetDto;
import com.rohit.dto.TweetRequest;
import com.rohit.model.User;
import com.rohit.service.TweetService;
import com.rohit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @PostMapping("/{username}")
    public TweetDto createTweet(@PathVariable String username, @RequestBody @Valid TweetRequest tweetRequest) {
        User user = userService.getUserByUsername(username);
        return TweetDto.ofEntity(tweetService.createTweet(user, tweetRequest.getContent()));
    }
}
