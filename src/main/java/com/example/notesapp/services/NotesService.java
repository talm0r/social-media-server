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

    /**
     * Add a new note
     * @param createNoteDto
     * @return ApiResponse(null)
     */
    public ApiResponse addNote(CreateNoteDto createNoteDto) {
        try {
            Note note = new Note();
            BeanUtils.copyProperties(createNoteDto,note);
            noteRepo.save(note);
            return new ApiResponse(200,"Note added successfully",null);
        }
        catch (Exception e) {
            return new ApiResponse(409,"error creating message",e.getMessage());
        }

    }

    /**
     * Update note
     * @param updateNoteDto
     * @return ApiResponse(null)
     */
    public ApiResponse updateNote(UpdateNoteDto updateNoteDto) {

        try {
            Optional<Note> OptionalNote = noteRepo.findById(updateNoteDto.getNoteId());
            Note note = OptionalNote.get();

            if(updateNoteDto.getNoteIcon() == null) {
                System.out.println("GET NOTE ICON IS NULL !!");
                BeanUtils.copyProperties(updateNoteDto,note,"noteRead","noteIcon");
            }
            else {
                BeanUtils.copyProperties(updateNoteDto,note,"noteRead");
                System.out.println("GET NOTE ICON IS NOT NULL :{");
            }
            System.out.println(updateNoteDto);

            noteRepo.save(note);

            return new ApiResponse(200,"Note updated successfully",null);
        }
        catch (Exception e) {
            return new ApiResponse(409,"error updating message",e.getMessage());
        }

    }

    /**
     * Update read\not read flag
     * @param readNoteDto
     * @return ApiResponse(null)
     */
    public ApiResponse setReadFlag(ReadNoteDto readNoteDto) {

        try {
            Optional<Note> OptionalNote = noteRepo.findById(readNoteDto.getNoteId());
            Note note = OptionalNote.get();
            BeanUtils.copyProperties(readNoteDto,note,"noteBody","noteTitle","noteUserId","noteSenderId","noteIcon");
            noteRepo.save(note);
            return new ApiResponse(200,"Note updated successfully",null);
        }
        catch (Exception e) {
            return new ApiResponse(409,"error updating read flag message",e.getMessage());
        }

    }

    /**
     * Checks user validation(token), then get his notes
     * @param validateUserDto
     * @return ApiResponse(List of IGetNoteDto(notes))
     */

    public ApiResponse getUserNotes(ValidateUserDto validateUserDto) {
        try {
            Optional<User> user = userRepo.findByUserIdAndJWTtoken(validateUserDto.getUserId(), validateUserDto.getToken());
            if(user.isEmpty()) {
                return new ApiResponse(409, "Wrong token", null) ;
            }
            List<IGetNoteDto> userNotes = noteRepo.getAllNotes(validateUserDto.getUserId());
            return new ApiResponse(200,"Notes loaded successfully",userNotes);
        }

        catch (Exception e) {
            return new ApiResponse(409,"error getting inbox messages",e.getMessage());
        }

    }

    /**
     * Checks user validation(token), then get his sent notes
     * @param validateUserDto
     * @return ApiResponse(List of IGetSentNoteDto(notes))
     */
    public ApiResponse getUserSentNotes(ValidateUserDto validateUserDto) {
        try {
            Optional<User> user = userRepo.findByUserIdAndJWTtoken(validateUserDto.getUserId(), validateUserDto.getToken());

            if(user.isEmpty()) {
                return new ApiResponse(409, "Wrong token", null) ;
            }

            List<IGetSentNoteDto> userNotes = noteRepo.getSentNotes(validateUserDto.getUserId());

            return new ApiResponse(200,"Notes loaded successfully",userNotes);
        }
        catch (Exception e) {
            return new ApiResponse(409,"error getting outbox messages",e.getMessage());
        }

    }

    /**
     * Delete note from db
     * @param noteId
     * @return ApiResponse(null)
     */
    public ApiResponse deleteNote(int noteId) {
        try {
            Optional<Note> OptionalNote = noteRepo.findById(noteId);
            if(OptionalNote.isEmpty()) {
                return new ApiResponse(409, "No such note", null) ;
            }
            Note note = OptionalNote.get();
            noteRepo.delete(note);
            return new ApiResponse(200,"Note successfully deleted",null);
        }
        catch  (Exception e) {
            return new ApiResponse(409,"error getting outbox messages",e.getMessage());
        }

    }
}
