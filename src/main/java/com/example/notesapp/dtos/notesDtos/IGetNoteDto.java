package com.example.notesapp.dtos.notesDtos;

import java.util.Date;

public interface IGetNoteDto {
     int getNoteId();
     String getNoteTitle();
     String getNoteColor();
     int getNoteSenderId();
     int getNoteUserId();
     Boolean getNoteRead();
     String getNoteIcon();
     int getNotePriority();
     Date getNoteCreated();
     String getNoteBody();
     String getUserFirstName();
     String getUserLastName();
     String getUserImage();
     String getUserRole();
     String getUserPhone();
     String getUserLocation();

}

