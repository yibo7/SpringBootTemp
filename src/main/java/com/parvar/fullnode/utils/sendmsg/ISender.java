package com.parvar.fullnode.utils.sendmsg;

import javax.mail.MessagingException;

/**
 * 定义一个手机短信发送接口
 *
 * @author 蔡齐盛
 * @create 2017-12-06 15:35
 **/
public interface ISender {

    /**
     * 发送一个手机短信的接口
     * @param mobileNumber 接收手机短信的号码
     * @param msg   短信内容
     * @param userName  用户名
     * @return 返回结果:{"time":"20171206152417","msgId":"17120615241729665","errorMsg":"","code":"0"}
     */
    String sendMsg(String mobileNumber, String msg, String userName);
    void sendSimpleMail(String sTitle,String Body,String senTo,boolean isHtml) throws MessagingException;
}
