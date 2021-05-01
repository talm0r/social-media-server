package com.example.notesapp.dtos.notesDtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GetNoteDto {

    private int noteId;
    private String noteBody;
    private String noteColor;
    private int noteSenderId;
    private int noteUserId;
    private String noteTitle;
    private Boolean noteRead = false;
    private String noteIcon;
    private int notePriority;
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userImage;
    private String userEmail;
    private String userRole;
    private String userPhone;
    private String userLocation;




}
