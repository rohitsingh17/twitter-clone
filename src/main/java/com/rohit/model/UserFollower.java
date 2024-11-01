package com.rohit.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_follower")
@IdClass(UserFollower.UserFollowerId.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFollower {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "user_id", nullable = false)
    private User follower;

    // Inner class to represent composite key
    public static class UserFollowerId implements Serializable {
        private Long user;
        private Long follower;

        public UserFollowerId() {}

        public UserFollowerId(Long user, Long follower) {
            this.user = user;
            this.follower = follower;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserFollowerId that = (UserFollowerId) o;
            return user.equals(that.user) && follower.equals(that.follower);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, follower);
        }
    }
}
