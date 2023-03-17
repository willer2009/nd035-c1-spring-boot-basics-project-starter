package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class SignupController {
    private UserService userService;

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(User user, Model model, RedirectAttributes redirectAttributes){
        String errorDuringSignup = null;

        if(!userService.isUsernameStillAvailable(user.getUsername())){
            errorDuringSignup = "User name already in use!";
        }

        if(Objects.isNull(errorDuringSignup)){
            int addedRows = userService.createUser(user);
            if(addedRows < 0){
                errorDuringSignup = "An error happened during the signup. Please try again later!";
            }
        }
        if(Objects.isNull(errorDuringSignup)){
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        }
        model.addAttribute("signupError", errorDuringSignup);
        return "signup";
    }
}
