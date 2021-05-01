package com.example.notesapp.beans;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Note {

    @GeneratedValue
    @Id
    private int noteId;

    @NotNull
    private int noteSenderId;

    @NotNull
    private int noteUserId;

    @NotNull
    private String noteTitle;

    @NotNull
    private String noteBody;


    private Boolean noteRead = false;

    @NotNull
    private String noteColor;

    @NotNull
    private String noteIcon;

    @NotNull
    private int notePriority;


    private LocalDateTime noteCreated = LocalDateTime.now();
}
