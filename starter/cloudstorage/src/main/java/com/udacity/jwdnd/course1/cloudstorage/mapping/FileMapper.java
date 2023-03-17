package com.udacity.jwdnd.course1.cloudstorage.mapping;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
    "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId}")
    void deletebyId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File findByFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename} AND userid=#{userId}")
    File findByFileNameForUser(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> findAllFilesFromUser(Integer userId);
}
