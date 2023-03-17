package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public Credential findById(Integer credentialId){
        return credentialMapper.findById(credentialId);
    }

    public int createCredential(Credential credential){
        return credentialMapper.insert(credential);
    }

    public void update(Credential credential){
        credentialMapper.update(credential);
    }

    public void delete(Integer credentialId){
        credentialMapper.delete(credentialId);
    }

    public List<Credential> getAllCredentialsForUser(Integer userId){
        return credentialMapper.getAllCredential(userId).stream().map(credential -> decryptPassword(credential)).collect(Collectors.toList());
    }

    private Credential decryptPassword(Credential credential) {
        credential.setPasswordDecypted(this.encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }
}
