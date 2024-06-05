package com.example.RegisterLogin.Service;
import java.util.List;
import java.util.Optional;

import com.example.RegisterLogin.Dto.CommentDto;
import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.Comment;
import com.example.RegisterLogin.Entity.FriendRequest;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Dto.PostDTO;
import com.example.RegisterLogin.Response.LoginResponse;

public interface UserService {
    String addUser(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDTO);
    void updateUserProfile(UserProfileDto userProfileDto);
    UserProfileDto getUserProfile(Integer userId);
    UserDto getUserProfileWithUserDetails(Integer userId);
    List<UserDto> getAllUsers();
    List<UserDto> getFriendsByUserId(int userId);
    Optional<MapaDto> getMapaByUserId(int userId);
    List<MapaDto> getFriendsLocationByUserId(int userId); 
    void saveMapa(MapaDto mapaDto);
    void updateMapa(Mapa existingMapa, MapaDto mapaDto);
    List<MapaDto> getAllMapaDetails();

    PostDTO createPost(PostDTO postDto); // Método para crear un nuevo post
    List<PostDTO> getAllPosts(); // Método para obtener todos los posts
    void likePost(int postId);

     // Método para actualizar un post existente
     void updatePost(int postId, PostDTO postDto);

     // Método para eliminar un post existente
     void deletePost(int postId);
     PostDTO getPostById(int postId);

    // Métodos para comentarios
    Comment createComment(int postId, CommentDto commentDto);
    Comment updateComment(int commentId, CommentDto commentDto);
    void deleteComment(int commentId);
    List<CommentDto> getCommentsByPostId(int postId);

     //Métodos solicitudes de amistad 
     Long sendFriendRequest(Long senderId, Long receiverId);
     void acceptFriendRequest(Long requestId);
     List<FriendRequest> getPendingFriendRequests(Long userId);

     String getUsernameByUserId(int userId);
     void likeComment(int commentId);
}