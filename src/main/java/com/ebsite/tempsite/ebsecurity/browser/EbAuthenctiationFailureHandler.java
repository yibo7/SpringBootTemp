package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码失败，登录失败等的处理器
 */
@Component("ebAuthenctiationFailureHandler")
public class EbAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ILoginFailurePush loginFailurePush;

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if(loginFailurePush!=null)
        {
            loginFailurePush.OnLoginFailure(request,response,exception);
        }
        //logger.info("登录失败");
        String sJson = request.getQueryString();
        if (!StringUtils.isEmpty(sJson)&&sJson.indexOf("isjson")>-1) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage(),-100)));
        }else{

            super.onAuthenticationFailure(request, response, exception);

            //throw new ValidateCodeException(exception.getMessage());
        }


    }
}
