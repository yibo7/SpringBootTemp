package com.ebsite.tempsite.ebsecurity.core.valcode.email;

public interface EmailCodeSender {

    /**
     * @param emailadrr
     * @param code
     */
    void send(String emailadrr, String code) throws Exception;

}
