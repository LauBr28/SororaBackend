package com.example.RegisterLogin.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RegisterLogin.Entity.Mapa;

import java.util.List;

public interface MapaRepo extends JpaRepository<Mapa, Integer> {

    @Query("SELECT m FROM Mapa m WHERE m.user.id IN (SELECT f.friend.id FROM Friendship f WHERE f.user.id = :userId)")
    List<Mapa> findFriendsLocationByUserId(@Param("userId") int userId);
}