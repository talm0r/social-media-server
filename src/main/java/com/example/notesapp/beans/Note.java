package com.example.notesapp.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Note {

    @GeneratedValue
    @Id
    private int noteId;

    @Min(value =1 , message =  "Note sender id must be at least 1")
    private int noteSenderId;

    @Min(value =1 , message =  "Note user id must be at least 1")
    private int noteUserId;

    @NotEmpty
    private String noteTitle;

    @NotEmpty
    private String noteBody;


    private Boolean noteRead = false;

    @NotEmpty
    private String noteColor;


    private String noteIcon;


    private int notePriority;


    private LocalDateTime noteCreated = LocalDateTime.now();
}
