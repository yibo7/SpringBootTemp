package com.ebsite.tempsite.configs.enc;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfig {
    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        return new DefaultEncryptor();
    }
}
