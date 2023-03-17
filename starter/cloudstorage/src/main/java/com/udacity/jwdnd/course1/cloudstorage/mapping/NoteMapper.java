package com.udacity.jwdnd.course1.cloudstorage.mapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userdid) VALUES (#{noteTitle}, #{noteDescription}, #{userdId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    void deleteById(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> findAll(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId=#{id}")
    Note findById(Integer id);
}
