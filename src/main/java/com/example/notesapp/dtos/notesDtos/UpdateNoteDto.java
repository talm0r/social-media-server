package com.example.notesapp.dtos.notesDtos;


import lombok.Data;

@Data
public class UpdateNoteDto {
    private int noteId;

    private String noteTitle;
    private String noteBody;
    private String noteColor;
    private String noteIcon;
    private int notePriority;
}
