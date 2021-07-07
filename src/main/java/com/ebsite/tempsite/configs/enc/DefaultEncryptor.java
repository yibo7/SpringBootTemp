package com.ebsite.tempsite.configs.enc;
import com.ebsite.tempsite.utils.encrypt.EbEncode;
import org.jasypt.encryption.StringEncryptor;
public class DefaultEncryptor implements StringEncryptor {
    @Override
    public String encrypt(String message) {
        return EbEncode.encryptAes(message);
    }
    @Override
    public String decrypt(String encryptedMessage) {
        return EbEncode.decryptAes(encryptedMessage);
    }
}
