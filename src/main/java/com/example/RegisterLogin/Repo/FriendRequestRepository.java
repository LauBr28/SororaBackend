package com.example.RegisterLogin.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RegisterLogin.Entity.FriendRequest;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    // Consulta para verificar si existe una solicitud de amistad pendiente entre dos usuarios
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Consulta para obtener las solicitudes de amistad pendientes para un usuario específico
    List<FriendRequest> findByReceiverId(Long receiverId);
}
