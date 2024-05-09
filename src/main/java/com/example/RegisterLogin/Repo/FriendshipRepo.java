package com.example.RegisterLogin.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.RegisterLogin.Entity.Friendship;
import com.example.RegisterLogin.Entity.User;
import jakarta.transaction.Transactional;
import java.util.List;


public interface FriendshipRepo extends JpaRepository<Friendship, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friendship (user_id, friend_id) VALUES (:userId, :friendId)", nativeQuery = true)
    void addFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    @Query("SELECT f.friend FROM Friendship f WHERE f.user.id = :userId")
    List<User> findFriendsByUserId(@Param("userId") int userId);
}