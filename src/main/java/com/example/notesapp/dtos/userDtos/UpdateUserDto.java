package com.example.notesapp.dtos.userDtos;

import lombok.Data;

@Data
public class UpdateUserDto {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userImage;
    private String userRole;
    private String userPhone;
    private String userLocation;
}
