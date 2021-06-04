package com.parvar.fullnode.configs.enc;
import com.parvar.fullnode.utils.encrypt.EbEncode;
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
