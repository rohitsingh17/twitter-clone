package com.rohit.controller;

import com.rohit.dto.UserDto;
import com.rohit.model.User;
import com.rohit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{username}/follow/{followerUsername}")
    public String followUser(@PathVariable String username, @PathVariable String followerUsername){
        User user = userService.getUserByUsername(username);
        User follower = userService.getUserByUsername(followerUsername);
        userService.followUser(user, follower);
        return "User followed successfully";
    }

    @PostMapping("/{username}/unfollow/{followerUsername}")
    public String unfollowUser(@PathVariable String username, @PathVariable String followerUsername) {
        User user = userService.getUserByUsername(username);
        User follower = userService.getUserByUsername(followerUsername);
        userService.unfollowUser(user, follower);
        return "User unfollowed successfully";
    }

    @GetMapping("/{username}/followers")
    public List<UserDto> getFollowers(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return userService.getFollowers(user)
                .stream()
                .map(follower -> UserDto.ofEntity(follower.getFollower()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}/following")
    public List<UserDto> getFollowing(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return userService.getFollowing(user)
                .stream()
                .map(following -> UserDto.ofEntity(following.getUser()))
                .collect(Collectors.toList());
    }
}
