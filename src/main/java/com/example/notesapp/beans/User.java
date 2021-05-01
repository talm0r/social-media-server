package com.example.notesapp.beans;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class User  implements Serializable {
    @Id
    @GeneratedValue
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userImage = "http://localhost:8080/upload/files/default.png";
    private String userRole;
    private String userPhone;
    private String userLocation;
    private String JWTtoken;

}
