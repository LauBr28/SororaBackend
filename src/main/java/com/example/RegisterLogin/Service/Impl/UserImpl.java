package com.example.RegisterLogin.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.User;
import com.example.RegisterLogin.Entity.UserProfile;
import com.example.RegisterLogin.Repo.UserRepo;
import com.example.RegisterLogin.Repo.UserProfileRepo;
import com.example.RegisterLogin.Response.LoginResponse;
import com.example.RegisterLogin.Service.UserService;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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


}
