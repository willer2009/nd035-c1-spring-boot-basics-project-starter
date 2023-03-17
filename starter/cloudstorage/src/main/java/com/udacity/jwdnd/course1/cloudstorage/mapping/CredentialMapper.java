package com.udacity.jwdnd.course1.cloudstorage.mapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert(("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})"))
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credential=#{credentialId}")
    Credential findById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getAllCredential(Integer userId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentials=#{credential}")
    void update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    void delete(Integer credentialId);
}
