package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable(value= "id") Integer noteid, RedirectAttributes redirectAttributes){
        try{
            this.noteService.delete(noteid);
            redirectAttributes.addFlashAttribute("noteSuccess", true);
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("noteError", true);
        }
        return "redirect:/result";
    }
}
