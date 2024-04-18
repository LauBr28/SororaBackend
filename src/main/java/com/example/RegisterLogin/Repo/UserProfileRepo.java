package com.example.RegisterLogin.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.RegisterLogin.Entity.UserProfile;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Integer> {
    // Aquí puedes agregar métodos personalizados de ser necesario
    Optional<UserProfile> findByUserId(Integer userId);
}