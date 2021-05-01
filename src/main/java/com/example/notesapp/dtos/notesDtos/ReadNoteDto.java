package com.example.notesapp.dtos.notesDtos;

import lombok.Data;

@Data
public class ReadNoteDto {
    int noteId;
    Boolean noteRead;
}
