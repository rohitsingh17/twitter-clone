package com.rohit.repository;

import com.rohit.model.User;
import com.rohit.model.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, UserFollower.UserFollowerId> {
    boolean existsByUserAndFollower(User user, User follower);
    Optional<UserFollower> findByUserAndFollower(User user, User follower);
    List<UserFollower> findByFollower(User follower);
    List<UserFollower> findByUser(User user);
}
