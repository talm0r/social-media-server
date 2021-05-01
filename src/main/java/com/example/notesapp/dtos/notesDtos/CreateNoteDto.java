package com.example.notesapp.dtos.notesDtos;


import lombok.Data;

import java.util.Date;

@Data
public class CreateNoteDto {
    private int noteSenderId;
    private int noteUserId;
    private String noteTitle;
    private String noteBody;
    private String noteColor;
    private String noteIcon;
    private int notePriority;
}
