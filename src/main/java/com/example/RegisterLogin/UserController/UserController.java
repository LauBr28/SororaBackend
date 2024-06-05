package com.example.RegisterLogin.UserController;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.Comment;
import com.example.RegisterLogin.Entity.FriendRequest;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Response.LoginResponse;
import com.example.RegisterLogin.Dto.CommentDto;
import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Dto.PostDTO;
import com.example.RegisterLogin.Service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDto userDto)
    {
        String id = userService.addUser(userDto);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto)
    {
        LoginResponse loginResponse = userService.loginUser(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        // Lógica para actualizar el perfil de usuario utilizando el UserService
        userService.updateUserProfile(userProfileDto);
        return ResponseEntity.ok("Profile updated successfully");
    
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam Integer userId) {
       UserDto userDto = userService.getUserProfileWithUserDetails(userId);
       return ResponseEntity.ok(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    
    @GetMapping("/friends")
    public ResponseEntity<List<UserDto>> getFriendsByUserId(@RequestParam int userId) {
        List<UserDto> friends = userService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friends);
    }


    @GetMapping("/friends/location")
    public ResponseEntity<List<MapaDto>> getFriendsLocationByUserId(@RequestParam int userId) {
        List<MapaDto> friendsLocation = userService.getFriendsLocationByUserId(userId);
        return ResponseEntity.ok(friendsLocation);
    }

    @PostMapping("/location")
    public ResponseEntity<String> saveUserLocation(@RequestBody MapaDto mapaDto) {
        try {
            Optional<MapaDto> existingMapaOptional = userService.getMapaByUserId(mapaDto.getUserId());
            System.out.println("Existing Mapa Optional: " + existingMapaOptional);
            if (existingMapaOptional.isPresent()) {
                // Ya existe una ubicación, actualizarla
                MapaDto existingMapaDto = existingMapaOptional.get();
                System.out.println("Existing Mapa Optional: " + existingMapaDto);
                existingMapaDto.setLongitud(mapaDto.getLongitud());
                existingMapaDto.setLatitud(mapaDto.getLatitud());
                existingMapaDto.setDateTime(mapaDto.getDateTime());

                // Convertir el MapaDto actualizado a Mapa
                Mapa existingMapa = convertMapaDtoToEntity(existingMapaDto);
                System.out.println("Existing Mapa: " + existingMapa);

                userService.updateMapa(existingMapa, existingMapaDto);
                return ResponseEntity.ok("Location updated successfully");
            } else {
                // No existe una ubicación, guardarla como nueva
                userService.saveMapa(mapaDto);
                return ResponseEntity.ok("Location saved successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving location: " + e.getMessage());
        }
    }

    // Método para convertir MapaDto a Mapa
    private Mapa convertMapaDtoToEntity(MapaDto mapaDto) {
        Mapa mapa = new Mapa();
        mapa.setLongitud(mapaDto.getLongitud());
        mapa.setLatitud(mapaDto.getLatitud());
        mapa.setDateTime(mapaDto.getDateTime());
        // Puedes establecer otras propiedades si es necesario
        return mapa;
    }

    @GetMapping("/mapa/details")
    public ResponseEntity<List<MapaDto>> getAllMapaDetails() {
        List<MapaDto> mapaDetails = userService.getAllMapaDetails();
        return ResponseEntity.ok(mapaDetails);
    }
    @PostMapping("/post/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
        PostDTO createdPost = userService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/posts/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> allPosts = userService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @PostMapping("/post/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable int postId) {
        try {
            userService.likePost(postId);
            return ResponseEntity.ok("Post liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error liking post: " + e.getMessage());
        }
    }

        // Actualizar un post existente
    @PostMapping("/post/update/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody PostDTO postDto) {
        try {
            userService.updatePost(postId, postDto);
            return ResponseEntity.ok("Post updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating post: " + e.getMessage());
        }
    }

    // Eliminar un post existente
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        try {
            userService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting post: " + e.getMessage());
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId) {
        PostDTO postDto = userService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/friendRequest/send/{senderId}/{receiverId}")
    public ResponseEntity<Map<String, Long>> sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            Long requestId = userService.sendFriendRequest(senderId, receiverId);
            Map<String, Long> response = new HashMap<>();
            response.put("requestId", requestId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   @PostMapping("/friendRequest/accept/{requestId}")
   public ResponseEntity<String> acceptFriendRequest(@PathVariable Long requestId) {
       try {
           userService.acceptFriendRequest(requestId);
           return ResponseEntity.ok("Friend request accepted successfully");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error accepting friend request: " + e.getMessage());
       }
   }
   
   // Ruta para obtener solicitudes de amistad pendientes
   @GetMapping("/friendRequest/pending/{userId}")
   public ResponseEntity<List<FriendRequest>> getPendingFriendRequests(@PathVariable Long userId) {
       try {
           List<FriendRequest> pendingRequests = userService.getPendingFriendRequests(userId);
           return ResponseEntity.ok(pendingRequests);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
   }

   @PostMapping("/comment/create/{postId}")
   public ResponseEntity<CommentDto> createComment(@PathVariable int postId, @RequestBody CommentDto commentDto) {
       Comment createdComment = userService.createComment(postId, commentDto);
       CommentDto createdCommentDto = convertToDto(createdComment);
       return ResponseEntity.ok(createdCommentDto);
   }

   @PutMapping("/comment/update/{commentId}")
   public ResponseEntity<CommentDto> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto) {
       Comment updatedComment = userService.updateComment(commentId, commentDto);
       CommentDto updatedCommentDto = convertToDto(updatedComment);
       return ResponseEntity.ok(updatedCommentDto);
   }

   @DeleteMapping("/comment/delete/{commentId}")
   public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
       userService.deleteComment(commentId);
       return ResponseEntity.ok("Comment deleted successfully");
   }

   @GetMapping("/comments/{postId}")
   public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable int postId) {
       List<CommentDto> comments = userService.getCommentsByPostId(postId);
       return ResponseEntity.ok(comments);
   }

   private CommentDto convertToDto(Comment comment) {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(comment.getCommentId());
    commentDto.setPostId(comment.getPost().getPostId());
    commentDto.setUserId(comment.getUserId());
    commentDto.setUsername(comment.getUsername());
    commentDto.setDateTime(comment.getDateTime());
    commentDto.setContent(comment.getContent());
    commentDto.setLikes(comment.getLikes()); 
    return commentDto;
    }

    @GetMapping("/username/{userId}")
    public ResponseEntity<String> getUsernameByUserId(@PathVariable int userId) {
        try {
            String username = userService.getUsernameByUserId(userId);
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error fetching username: " + e.getMessage());
        }
    }

    @PostMapping("/comment/like/{commentId}")
    public ResponseEntity<String> likeComment(@PathVariable int commentId) {
        try {
            userService.likeComment(commentId);
            return ResponseEntity.ok("Comment liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error liking comment: " + e.getMessage());
        }
    }


}