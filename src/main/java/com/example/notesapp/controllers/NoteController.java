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

    String message = "Could not";
    @Autowired
    NotesService notesService;

    @PostMapping("add")
    public ApiResponse add(@RequestBody CreateNoteDto createNoteDto) {
        try {
            return this.notesService.addNote(createNoteDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "Add note",e.getMessage());
        }

    }

    @PostMapping("getUserNotes")
    public ApiResponse getUserNotes(@RequestBody ValidateUserDto validateUserDto) {
        try {
            return this.notesService.getUserNotes(validateUserDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "get user notes",e.getMessage());
        }

    }

    @PostMapping("getUserSentNotes")
    public ApiResponse getUserSentNotes(@RequestBody ValidateUserDto validateUserDto) {
        try {
            return this.notesService.getUserSentNotes(validateUserDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "get user sent notes",e.getMessage());
        }

    }

    @PutMapping("/update")
    public ApiResponse updateNote(@RequestBody UpdateNoteDto note) {
        try {
            return this.notesService.updateNote(note);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "update note",e.getMessage());
        }

    }

    @PutMapping("/readNote")
    public ApiResponse setReadFlag(@RequestBody ReadNoteDto readNoteDto) {
        try {
            return this.notesService.setReadFlag(readNoteDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "set flag of note",e.getMessage());
        }

    }

    @DeleteMapping("/delete/{noteId}")
    public ApiResponse deleteNote(@PathVariable int noteId) {
        try {
            return this.notesService.deleteNote(noteId);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "delete Note",e.getMessage());
        }
    }
}
