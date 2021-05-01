package com.example.notesapp.controllers;


import com.example.notesapp.dtos.notesDtos.CreateNoteDto;
import com.example.notesapp.dtos.notesDtos.ReadNoteDto;
import com.example.notesapp.dtos.notesDtos.UpdateNoteDto;
import com.example.notesapp.dtos.userDtos.ValidateUserDto;
import com.example.notesapp.services.NotesService;
import com.example.notesapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notes")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    NotesService notesService;

    @PostMapping("add")
    public ApiResponse add(@RequestBody CreateNoteDto createNoteDto) {

        System.out.println(createNoteDto);
        return this.notesService.addNote(createNoteDto);
    }

    @PostMapping("getUserNotes")
    public ApiResponse getUserNotes(@RequestBody ValidateUserDto validateUserDto) {
        System.out.println(validateUserDto.getToken());
        return this.notesService.getUserNotes(validateUserDto);
    }

    @PostMapping("getUserSentNotes")
    public ApiResponse getUserSentNotes(@RequestBody ValidateUserDto validateUserDto) {
        System.out.println(validateUserDto.getToken());
        return this.notesService.getUserSentNotes(validateUserDto);
    }

    @PutMapping("/update")
    public ApiResponse updateNote(@RequestBody UpdateNoteDto note) {
       return this.notesService.updateNote(note);

    }
    @PutMapping("/readNote")
    public ApiResponse setReadFlag(@RequestBody ReadNoteDto readNoteDto) {
       return this.notesService.setReadFlag(readNoteDto);

    }
}
