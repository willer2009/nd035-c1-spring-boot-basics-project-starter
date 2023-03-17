package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private CredentialService credentialService;
    private NoteService noteService;
    private FileUploadService fileUploadService;
    private UserService userService;

    @GetMapping("/result")
    public String result(){
        return "result";
    }

    @GetMapping("/home")
    public String getHomepage(Authentication authentication, Note note, Credential credential, Model model){
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        model.addAttribute("credentials", this.credentialService.getAllCredentialsForUser(userId));
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("files", this.fileUploadService.getAllFiles(userId));
        return "home";
    }

}
