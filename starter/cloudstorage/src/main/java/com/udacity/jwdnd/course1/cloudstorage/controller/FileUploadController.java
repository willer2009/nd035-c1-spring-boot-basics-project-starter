package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class FileUploadController {
    private UserService userService;
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile uploadedFile, Authentication authentication,
                             RedirectAttributes redirectAttributes) throws Exception{
        if(uploadedFile.isEmpty()){
            redirectAttributes.addFlashAttribute("fileError", "Please Select a file to upload");
        }else{
            try{
                String file = uploadedFile.getOriginalFilename();
                Integer userId = userService.getUser(authentication.getName()).getUserId();

                if(fileUploadService.findFileByNameAndUser(file, userId) != null){
                    redirectAttributes.addFlashAttribute("fileError", "A file with the same name already exists!");
                }else{
                    File fileForDb = new File();
                    fileForDb.setUserId(userId);
                    fileForDb.setFilename(uploadedFile.getOriginalFilename());
                    fileForDb.setFileSize(String.valueOf(uploadedFile.getSize()));
                    fileForDb.setContentType(uploadedFile.getContentType());
                    fileForDb.setFileData(uploadedFile.getBytes());

                    int count = fileUploadService.insert(fileForDb);
                    if(count > 0){
                        redirectAttributes.addFlashAttribute("fileSuccess", "Successful upload.");
                    }else{
                        redirectAttributes.addFlashAttribute("fileError", "Upload not successful!");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("FileError", "Error during file upload!");
            }
        }

        return "redirect:/result";
    }


    @GetMapping("/files/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable(value = "id") Integer fileId, HttpServletResponse response) throws IOException {
        File file = fileUploadService.findById(fileId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachement").filename(file.getFilename()).build().toString());
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return ResponseEntity.ok().headers(httpHeaders).body(file.getFileData());
    }

    @GetMapping("/file/delete/{id}")
    public String deleteFile(@PathVariable(value= "id") Integer fieldId, RedirectAttributes redirectAttributes){
        try{
            fileUploadService.delete(fieldId);
            redirectAttributes.addFlashAttribute("fileSuccess", "The file was successfully deleted.");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("fileError", "Ann Error happened during the deletion of the file");
        }

        return "redirect:/result";
    }
}
