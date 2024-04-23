package com.example.RegisterLogin.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.User;
import com.example.RegisterLogin.Entity.UserProfile;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Entity.Post;
import com.example.RegisterLogin.Repo.UserRepo;
import com.example.RegisterLogin.Repo.FriendshipRepo;
import com.example.RegisterLogin.Repo.UserProfileRepo;
import com.example.RegisterLogin.Repo.MapaRepo;
import com.example.RegisterLogin.Repo.PostRepo;
import com.example.RegisterLogin.Response.LoginResponse;
import com.example.RegisterLogin.Service.UserService;


@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MapaRepo mapaRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FriendshipRepo friendshipRepo;

    @Autowired
    private PostRepo postRepo;


    @Override
    public String addUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getEmail(),

        this.passwordEncoder.encode(userDto.getPassword()));

        // Set user profile if available
        if (userDto.getUserProfileDto() != null) {
            UserProfileDto profileDto = userDto.getUserProfileDto();
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user); // Establecer la relación con el usuario
            userProfile.setDescription(profileDto.getDescription());
            userProfile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
            user.setUserProfile(userProfile);
        }

        userRepo.save(user);
        return user.getUsername();
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {

        User user1 = userRepo.findByEmail(loginDto.getEmail());
        if (user1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDto.getEmail(),
                        encodedPassword);
                if (user.isPresent()) {
                    LoginResponse loginResponse = new LoginResponse("Login Success", true);
                    loginResponse.setId(user1.getId()); // Establecer el ID del usuario en el LoginResponse
                    return loginResponse;
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }
        } else {
            return new LoginResponse("Email not exits", false);
        }

    }

    @Override
    public void updateUserProfile(UserProfileDto userProfileDto) {
        // Obtener el usuario por su ID
        User user = userRepo.findById(userProfileDto.getUserId())
                .orElseThrow(() -> new RuntimeException("-----------User not found------------"));

        // Obtener el perfil del usuario (si existe)
        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            // Si el perfil no existe, crear uno nuevo y asociarlo al usuario
            userProfile = new UserProfile();
            userProfile.setUser(user);
            user.setUserProfile(userProfile);
        }

        // Actualizar la descripción y la URL de la imagen del perfil
        userProfile.setDescription(userProfileDto.getDescription());
        userProfile.setProfilePictureUrl(userProfileDto.getProfilePictureUrl());

        // Guardar los cambios en el perfil
        userProfileRepo.save(userProfile);
    }

    @Override
    public UserProfileDto getUserProfile(Integer userId) {
        // Lógica para obtener el perfil del usuario desde la base de datos u otra fuente de datos
        Optional<UserProfile> userProfileOptional = userProfileRepo.findByUserId(userId);
        
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            // Convertir UserProfile a UserProfileDto directamente aquí
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setUserId(userProfile.getUser().getId());
            userProfileDto.setDescription(userProfile.getDescription());
            userProfileDto.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            // Otros campos si es necesario

            return userProfileDto;
        } else {
            // Manejar el caso en que no se encuentre el perfil del usuario
            throw new RuntimeException("Perfil de usuario no encontrado para el ID: " + userId);
        }
    }
    
    @Override
    public UserDto getUserProfileWithUserDetails(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserProfileDto userProfileDto = getUserProfile(userId); // Obtener el UserProfileDto
            
            // Crear un nuevo UserDto combinando la información del usuario y su perfil
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setFirstname(user.getFirstname());
            userDto.setLastname(user.getLastname());
            userDto.setEmail(user.getEmail());
            userDto.setUserProfileDto(userProfileDto); // Establecer el UserProfileDto
            
            return userDto;
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    
    public UserDto convertToDto(User user) {
        UserDto.UserDtoBuilder userDtoBuilder = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail());
    
        if (user.getUserProfile() != null) {
            // Crear un UserProfileDto a partir del UserProfile
            UserProfile userProfile = user.getUserProfile();
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setUserId(userProfile.getId());
            userProfileDto.setDescription(userProfile.getDescription());
            userProfileDto.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            
            userDtoBuilder.userProfileDto(userProfileDto); // Agregar el UserProfileDto al UserDto si existe
        } else {
            // Si el perfil no existe, asignar un perfil vacío
            UserProfileDto userProfileDto = new UserProfileDto();
            userDtoBuilder.userProfileDto(userProfileDto); // Agregar el perfil vacío al UserDto
        }
    
        return userDtoBuilder.build();
    }

    public MapaDto convertMapaToDto(Mapa mapa) {
        MapaDto mapaDto = new MapaDto();
        mapaDto.setUserId(mapa.getUser().getId());
        mapaDto.setLongitud(mapa.getLongitud());
        mapaDto.setLatitud(mapa.getLatitud());
        mapaDto.setDateTime(mapa.getDateTime());
        return mapaDto;
    }

    @Override
    public List<MapaDto> getAllMapaDetails() {
        List<Mapa> mapaList = mapaRepo.findAll();
        return mapaList.stream().map(this::convertMapaToDto).collect(Collectors.toList());
    }
    
    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void connectUsersAsFriends(int userId, int friendId) {
        friendshipRepo.addFriend(userId, friendId);
    }

    @Override
    public List<UserDto> getFriendsByUserId(int userId) {
        List<User> friends = friendshipRepo.findFriendsByUserId(userId);
        return friends.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<MapaDto> getFriendsLocationByUserId(int userId) {
        List<Mapa> locations = mapaRepo.findFriendsLocationByUserId(userId);
        return locations.stream().map(this::convertMapaToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MapaDto> getMapaByUserId(int userId) {
        Optional<Mapa> mapaOptional = mapaRepo.findByUserId(userId);
        return mapaOptional.map(this::convertMapaToDto); // Convierte a MapaDto si se encuentra
    }

    public void saveMapa(MapaDto mapaDto) {
        // Buscar el usuario en la base de datos por su ID
        Optional<User> userOptional = userRepo.findById(mapaDto.getUserId());
    
        // Verificar si el usuario existe
        if (userOptional.isPresent()) {
            // Crear un nuevo objeto Mapa y asignarle los valores del MapaDto
            Mapa mapa = new Mapa();
            mapa.setLongitud(mapaDto.getLongitud());
            mapa.setLatitud(mapaDto.getLatitud());
            mapa.setDateTime(mapaDto.getDateTime());
            
            // Establecer la relación con el usuario
            User user = userOptional.get();
            mapa.setUser(user);
            
            // Guardar el objeto Mapa en la base de datos
            mapaRepo.save(mapa);
        } else {
            // Manejar el caso en que no se encuentre el usuario
            throw new RuntimeException("Usuario no encontrado con el ID: " + mapaDto.getUserId());
        }
    }

    public void updateMapa(Mapa existingMapa, MapaDto mapaDto) {
        // Obtener el usuario por su ID
        Mapa mapa = mapaRepo.findByUserId(mapaDto.getUserId())
                .orElseThrow(() -> new RuntimeException("-----------User not found------------"));

        System.out.println("ExistingMapa desde update " + existingMapa);
        System.out.println("MapaDto desde update " + mapaDto);
        mapa.setLongitud(existingMapa.getLongitud());
        mapa.setLatitud(existingMapa.getLatitud());
        mapa.setDateTime(existingMapa.getDateTime());
        /* existingMapa.setLongitud(mapaDto.getLongitud());
        existingMapa.setLatitud(mapaDto.getLatitud());
        existingMapa.setDateTime(mapaDto.getDateTime()); */
        mapaRepo.save(mapa);
    }
    

    @Override
    public Post addPost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post editPost(Post post) {
        return postRepo.saveAndFlush(post);
    }

    @Override
    public void deletePost(int id) {
        postRepo.deleteById(id);
    }

    @Override
    public void likePost(int postId) {
        postRepo.likePost(postId);
    }

    @Override
    public Optional<Post> getPostById(int id) {
        return postRepo.findById(id);
    }
    
}
