package com.example.notesapp.services;


import com.example.notesapp.beans.Note;
import com.example.notesapp.beans.User;
import com.example.notesapp.dtos.notesDtos.*;
import com.example.notesapp.dtos.userDtos.ValidateUserDto;
import com.example.notesapp.repo.NoteRepository;
import com.example.notesapp.repo.UserRepository;
import com.example.notesapp.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotesService {

    @Autowired
    NoteRepository noteRepo;
    @Autowired
    UserRepository userRepo;

    public ApiResponse addNote(CreateNoteDto createNoteDto) {
        Note note = new Note();
        BeanUtils.copyProperties(createNoteDto,note);
        noteRepo.save(note);

        return new ApiResponse(200,"Note added successfully",null);
    }
    public ApiResponse updateNote(UpdateNoteDto updateNoteDto) {

        Optional<Note> OptionalNote = noteRepo.findById(updateNoteDto.getNoteId());
        Note note = OptionalNote.get();
        System.out.println(updateNoteDto);
        if(updateNoteDto.getNoteIcon() == null) {
            System.out.println("GET NOTE ICON IS NULL !!");
        }
        else {
            System.out.println("GET NOTE ICON IS NOT NULL :{");
            System.out.println("GET NOTE ICON IS NOT NULL :{");
            System.out.println("GET NOTE ICON IS NOT NULL :{");
        }
        BeanUtils.copyProperties(updateNoteDto,note,"noteRead");
        BeanUtils.copyProperties(updateNoteDto,note);

        noteRepo.save(note);

        return new ApiResponse(200,"Note updated successfully",null);
    }
    public ApiResponse setReadFlag(ReadNoteDto readNoteDto) {

        Optional<Note> OptionalNote = noteRepo.findById(readNoteDto.getNoteId());
        Note note = OptionalNote.get();
        BeanUtils.copyProperties(readNoteDto,note,"noteBody","noteTitle","noteUserId","noteSenderId","noteIcon");
        noteRepo.save(note);
        return new ApiResponse(200,"Note updated successfully",null);
    }

    public ApiResponse getUserNotes(ValidateUserDto validateUserDto) {
       Optional<User> user = userRepo.findByUserIdAndJWTtoken(validateUserDto.getUserId(), validateUserDto.getToken());

        if(user.isEmpty()) {
            return new ApiResponse(409, "Wrong token", null) ;
        }

        List<IGetNoteDto> userNotes = noteRepo.getAllNotes(validateUserDto.getUserId());

       return new ApiResponse(200,"Notes loaded successfully",userNotes);
    }
    public ApiResponse getUserSentNotes(ValidateUserDto validateUserDto) {
       Optional<User> user = userRepo.findByUserIdAndJWTtoken(validateUserDto.getUserId(), validateUserDto.getToken());

        if(user.isEmpty()) {
            return new ApiResponse(409, "Wrong token", null) ;
        }

        List<IGetSentNoteDto> userNotes = noteRepo.getSentNotes(validateUserDto.getUserId());

       return new ApiResponse(200,"Notes loaded successfully",userNotes);
    }
}
