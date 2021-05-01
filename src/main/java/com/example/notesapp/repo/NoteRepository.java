package com.example.notesapp.repo;

import com.example.notesapp.beans.Note;
import com.example.notesapp.dtos.notesDtos.IGetNoteDto;
import com.example.notesapp.dtos.notesDtos.IGetSentNoteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Integer> {

    List<Note> findByNoteUserId(int userId);
    @Query(
            value = "SELECT " +
                    "note.note_id as noteId, note.note_title as noteTitle, note.note_color as noteColor, note.note_sender_id as noteSenderId, " +
                    "note.note_user_id as noteUserId,  note.note_read as noteRead, note.note_icon as noteIcon, note.note_priority as notePriority," +
                    "note.note_body as noteBody,note.note_created as noteCreated, " +
                    "user.user_email as userEmail,user_first_name as userFirstName,user_last_name as userLastName," +
                    "user_image as userImage, user_location as userLocation,user_phone as userPhone, user_role as userRole " +
                    "from note inner join user on user_id = note.note_sender_id  where note_user_id  = :userId",
    nativeQuery = true)

    List<IGetNoteDto> getAllNotes(@Param("userId")int userId);
    @Query(
            value = "SELECT DISTINCT note.note_id as noteId, note.note_title as noteTitle, note.note_color as noteColor, note.note_sender_id as noteSenderId, " +
                    "note.note_user_id as noteUserId, note.note_read as noteRead, note.note_icon as noteIcon, " +
                    "note.note_priority as notePriority,note.note_body as noteBody," +
                    "note.note_created as noteCreated, sender.user_email as userEmail,sender.user_first_name as userFirstName," +
                    "sender.user_last_name as userLastName,sender.user_image as userImage, " +
                    "sender.user_location as userLocation,sender.user_phone as userPhone, sender.user_role as userRole , " +
                    "getter.user_first_name as getterFirstName, getter.user_last_name as getterLastName, " +
                    "getter.user_location as getterUserLocation,getter.user_image as getterUserImage," +
                    "getter.user_phone as getterUserPhone, getter.user_role as getterUserRole " +
                    "from note "+
                    "inner join user sender on sender.user_id = note.note_sender_id " +
                    "inner join user getter on getter.user_id = note.note_user_id where note_sender_id = :userId",
            nativeQuery = true)
    List<IGetSentNoteDto> getSentNotes(@Param("userId")int userId);
}
