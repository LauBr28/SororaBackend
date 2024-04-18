package com.example.RegisterLogin.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MapaDto {
    private int userId; // ID del usuario que hizo el comentario
    private double longitud;
    private double latitud;
    private List<FriendshipDto> friends; // Lista de comentarios

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLongitud(){
        return longitud;
    }

    public void setLongitud(double longitud){
        this.longitud = longitud;
    }

    public double getLatitud(){
        return latitud;
    }

    public void setLatitud(double latitud){
        this.latitud = latitud;
    }

    public List<FriendshipDto> getFriends() {
        return friends;
    }
    public void setComments(List<FriendshipDto> friends) {
        this.friends = friends;
    }
}
