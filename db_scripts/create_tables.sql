--Create database
CREATE DATABASE twitter_clone;

-- Use the created database
\c twitter_clone;

-- User table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    follower_count INT DEFAULT 0 NOT NULL,
    following_count INT DEFAULT 0 NOT NULL,
    CHECK (follower_count >= 0),
    CHECK (following_count >= 0)
);

-- Index on username for quick lookups
CREATE INDEX idx_user_username ON users(username);

-- Tweets Table
CREATE TABLE tweets (
    tweet_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content VARCHAR(160) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    likes INT DEFAULT 0 NOT NULL,
    retweet INT DEFAULT 0 NOT NULL,
    CHECK (likes >= 0),
    CHECK (retweet >= 0),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Index on user_id to optimize query performance when fetching tweets by user
CREATE INDEX idx_tweet_user_id ON tweets(user_id);

-- User Follower Table (Self-referencing Many-to-Many for following relationships)
CREATE TABLE user_follower (
    user_id BIGINT NOT NULL,
    follower_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, follower_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (follower_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CHECK (user_id != follower_id)
);

-- Indexes on user_id and follower_id to optimize queries involving follower relationships
CREATE INDEX idx_user_follower_user ON user_follower(user_id);
CREATE INDEX idx_user_follower_follower ON user_follower(follower_id);