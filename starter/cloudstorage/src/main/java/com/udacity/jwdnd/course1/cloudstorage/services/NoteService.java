package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private NoteMapper noteMapper;

    public Integer createNote(Note note, Integer userId){
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId));
    }

    public Integer updateNote(Note note){
        return noteMapper.update(note);
    }

    public List<Note> getAllNotes(Integer userId){
        return noteMapper.findAll(userId);
    }

    public void delete(Integer noteId){
        noteMapper.deleteById(noteId);
    }
    public Note findById(Integer id){
        return noteMapper.findById(id);
    }
}
