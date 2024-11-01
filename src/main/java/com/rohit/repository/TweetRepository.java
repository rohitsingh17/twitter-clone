package com.rohit.repository;

import com.rohit.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUser_UserIdInOrderByCreatedAtDesc(List<Long> userIds);
}
