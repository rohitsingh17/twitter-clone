package com.rohit.service;

import com.rohit.dto.UserDto;
import com.rohit.exception.DuplicateFollowException;
import com.rohit.exception.InvalidInputException;
import com.rohit.exception.UserNotFoundException;
import com.rohit.model.User;
import com.rohit.model.UserFollower;
import com.rohit.repository.UserFollowerRepository;
import com.rohit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDto convertToDto(User user){
        return UserDto.ofEntity(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    public void followUser(User user, User follower){
        if(userFollowerRepository.existsByUserAndFollower(user, follower)){
            throw new DuplicateFollowException("User with ID " + follower.getUserId() + " is already following user with ID " + user.getUserId());
        }

        // Increment counts
        follower.setFollowingCount(follower.getFollowingCount() + 1);
        user.setFollowerCount(user.getFollowerCount() + 1);

        // Save updated user counts
        userRepository.save(follower);
        userRepository.save(user);

        UserFollower userFollower = UserFollower.builder()
                .user(user)
                .follower(follower)
                .build();
        userFollowerRepository.save(userFollower);
    }

    public void unfollowUser(User user, User follower) {
        UserFollower userFollower = userFollowerRepository.findByUserAndFollower(user, follower)
                .orElseThrow(() -> new InvalidInputException("User with ID " + follower.getUserId() + " is not following user with ID " + user.getUserId()));

        // Decrement counts
        follower.setFollowingCount(follower.getFollowingCount() - 1);
        user.setFollowerCount(user.getFollowerCount() - 1);

        // Save updated user counts
        userRepository.save(follower);
        userRepository.save(user);

        userFollowerRepository.delete(userFollower);
    }

    public List<UserFollower> getFollowers(User user) {
        return userFollowerRepository.findByUser(user);
    }

    public List<UserFollower> getFollowing(User follower) {
        return userFollowerRepository.findByFollower(follower);
    }


}
