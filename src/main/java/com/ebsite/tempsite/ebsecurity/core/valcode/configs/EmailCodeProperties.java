package com.ebsite.tempsite.ebsecurity.core.valcode.configs;

import lombok.Data;

@Data
public class EmailCodeProperties extends SmsCodeProperties {

    public EmailCodeProperties() {
        setLength(6);
    }


}
