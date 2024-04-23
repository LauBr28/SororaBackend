package com.example.RegisterLogin.payloadResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginMessage {
    String message;
    String status;
    

}
