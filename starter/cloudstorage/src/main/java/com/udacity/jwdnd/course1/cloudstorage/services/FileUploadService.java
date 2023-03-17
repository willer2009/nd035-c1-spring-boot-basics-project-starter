package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileUploadService {
    private final FileMapper fileMapper;

    public Integer insert(File file){
        return fileMapper.insert(file);
    }

    public List<File> getAllFiles(Integer userId){
        return this.fileMapper.findAllFilesFromUser(userId);
    }

    public void delete(Integer fileId){
        this.fileMapper.deletebyId(fileId);
    }

    public File findFileByNameAndUser(String filename, Integer userId){
        return this.fileMapper.findByFileNameForUser(filename, userId);
    }

    public File findById(Integer fileId){
        return this.fileMapper.findByFileId(fileId);
    }
}
