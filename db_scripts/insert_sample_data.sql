-- Sample Users
INSERT INTO users (username, password, salt, email, follower_count, following_count)
VALUES
    ('user1', 'hashed_password1', 'salt1', 'user1@example.com', 2, 1),
    ('user2', 'hashed_password2', 'salt2', 'user2@example.com', 1, 1),
    ('user3', 'hashed_password3', 'salt3', 'user3@example.com', 1, 2);

-- Sample Tweets
INSERT INTO tweets (user_id, content, likes, retweet)
VALUES
    (1, 'This is the first tweet from user1!', 5, 2),
    (1, 'Another tweet from user1 about tech.', 3, 1),
    (2, 'Hello from user2! Excited to join!', 8, 3),
    (2, 'Learning SQL and databases!', 2, 0),
    (3, 'User3 here! Loving the platform.', 10, 5),
    (3, 'Random thoughts by user3.', 6, 2);

-- Sample Follower Relationships
-- User1 follows User2 and User3
-- User2 follows User1
-- User3 follows User1 and User2

INSERT INTO user_follower (user_id, follower_id)
VALUES
    (2, 1),  -- User1 follows User2
    (3, 1),  -- User1 follows User3
    (1, 2),  -- User2 follows User1
    (1, 3),  -- User3 follows User1
    (2, 3);  -- User3 follows User2