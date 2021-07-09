package com.ebsite.tempsite.ebsecurity.core.valcode.image;

import com.ebsite.tempsite.ebsecurity.core.valcode.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 *
 *
 */
@Component("imageValidateCodeProcessor") //image 为 EmValidateCodeType 中的小写，因为验证请求是通过EmValidateCodeType来查找ValidateCodeProcessor对象的
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected String getMobileEmail(ServletWebRequest request){
        return "";
    }
    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
    @Override
    public String allowMethod() {
        return "get";
    }
}