package com.rohit.dto;

import com.rohit.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private final Long userId;
    private final String username;
    private final String email;
    private final Integer followerCount;
    private final Integer followingCount;

    public static UserDto ofEntity(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .build();
    }
}
