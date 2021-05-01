package com.example.notesapp.dtos.userDtos;


import lombok.Data;

@Data
public class ValidateUserDto {
    private String token;
    private int userId;
}
