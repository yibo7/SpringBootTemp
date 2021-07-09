package com.ebsite.tempsite.ebsecurity.core.valcode.sms;

public interface SmsCodeSender {
    /**
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);

}
