package com.example.notesapp.dtos.userDtos;

import lombok.Data;

@Data
public class SignUpDto {

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;

}
