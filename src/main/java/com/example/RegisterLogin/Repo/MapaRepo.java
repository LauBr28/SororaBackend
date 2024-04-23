package com.example.RegisterLogin.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RegisterLogin.Entity.Mapa;

import java.util.List;
import java.util.Optional;

public interface MapaRepo extends JpaRepository<Mapa, Integer> {

    Optional<Mapa> findByUserId(int userId);
    List<Mapa> findAll();
    @Query("SELECT m FROM Mapa m WHERE m.user.id IN (SELECT f.friend.id FROM Friendship f WHERE f.user.id = :userId)")
    List<Mapa> findFriendsLocationByUserId(@Param("userId") int userId);
}