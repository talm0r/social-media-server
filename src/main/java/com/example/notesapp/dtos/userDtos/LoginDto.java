package com.example.notesapp.dtos.userDtos;

import lombok.Data;

@Data
public class LoginDto {
    private String userEmail;
    private String userPassword;
}
