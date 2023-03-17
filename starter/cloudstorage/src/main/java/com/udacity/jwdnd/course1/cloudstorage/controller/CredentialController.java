package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class CredentialController {
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;

    @GetMapping("/credentials/delete/{id}")
    public String deleteCredential(@PathVariable(value = "id") Integer credentialId, RedirectAttributes redirectAttributes){
        try{
            this.credentialService.delete(credentialId);
            redirectAttributes.addFlashAttribute("credentialSuccess", true);
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("credentialError", true);
        }
        return "redirect:/result";
    }

    @PostMapping("/credentials")
    public String createCredentials(Authentication authentication, @ModelAttribute("credential")Credential credential,
                                    RedirectAttributes redirectAttributes){
        try{
            Integer userId = this.userService.getUser(authentication.getName()).getUserId();
            credential.setUserid(userId);
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            String key = Base64.getEncoder().encodeToString(salt);
            credential.setKey(key);
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));

            if(Objects.nonNull(credentialService.findById(credential.getCredentialId()))){
                if(credentialService.createCredential(credential) > 0){
                    redirectAttributes.addFlashAttribute("credentialSuccess", true);
                }else{
                    redirectAttributes.addFlashAttribute("credentialError", true);
                }
            }else {
                credentialService.update(credential);
                redirectAttributes.addFlashAttribute("credentialError", true);
            }

        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("credentialError", true);
        }
        return "redirect:/result";
    }
}
