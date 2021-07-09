package com.ebsite.tempsite.ebsecurity.core;

import com.ebsite.tempsite.utils.encrypt.MD5Encrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EbPasswordEncoder implements PasswordEncoder
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public EbPasswordEncoder(){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword)
    {
       String smd5 =  MD5Encrypt.MD5Encode(rawPassword.toString());

        return bCryptPasswordEncoder.encode(smd5);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String smd5 =  MD5Encrypt.MD5Encode(rawPassword.toString());

        return bCryptPasswordEncoder.matches(smd5,encodedPassword);
    }
}
