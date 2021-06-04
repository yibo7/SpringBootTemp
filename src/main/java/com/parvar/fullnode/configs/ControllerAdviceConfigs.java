package com.parvar.fullnode.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parvar.fullnode.apihelper.ApiResult;
import com.parvar.fullnode.apihelper.ApiResultUtils;
import com.parvar.fullnode.exception.EbAuthorizeException;
import com.parvar.fullnode.exception.EbGlobalException;
import com.parvar.fullnode.settings.SiteSetting;
import com.parvar.fullnode.utils.DateUtils;
import com.parvar.fullnode.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


@ControllerAdvice
@Slf4j
public class ControllerAdviceConfigs  {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }
    @Autowired
    protected SiteSetting siteSetting;
    @ModelAttribute("sitename") //添加一个全局模板model属性
    public String getSiteName() {
        return siteSetting.getSiteName();
    }

    @ExceptionHandler(value = EbGlobalException.class)
    @ResponseBody
    public ApiResult handleEbGlobalException(EbGlobalException e){
        return ApiResultUtils.error(e.getCode(),e.getMessage());
    }
    @ExceptionHandler(value = EbAuthorizeException.class)
    @ResponseBody //返回格式为json
    public ApiResult handleAuthenticationException(EbAuthorizeException e){
        return ApiResultUtils.error(e.getCode(),e.getMessage());
    }


    /* 暂时不用
    @ModelAttribute("cbUser")
    public EbAdminUserDetails getCurrentName() {
        EbAdminUserDetails user = null;// = new EbAdminUserDetails();
        if(SecurityContextHolder.getContext().getAuthentication()!=null){
            if (!SecurityContextHolder.getContext().getAuthentication().getName().
                    equals("anonymousUser")) {
                Object obj =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                user = (EbAdminUserDetails)obj;
            }
        }
        return user;
    }

*/
    @Autowired
    private LocaleMessageService localeMessageService;
    @ModelAttribute("lang")
    public LocaleMessageService getLangs() {
        return localeMessageService;
    }

    /**
     * 未知的异常捕获处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult allUnknowExceptionHandler(HttpServletRequest request, Exception exception) throws JsonProcessingException{
        String error = logError(request, exception);
        log.error("发生未知异常："+error);
        //可以发送邮件通知开发
        return ApiResultUtils.error(-100,"发生未知错误！");
    }

    private String logError(HttpServletRequest request, Exception exception) throws JsonProcessingException
    {
        log.error("发生未知异常:", exception);
        StringWriter sw = new StringWriter();
        sw.append(String.format("Date:{%s};\n", DateUtils.formatDateTime(new Date())));
        sw.append(String.format("url:{%s}产生错误;\n", request.getRequestURI()));
        sw.append(String.format("请求IP:{%s};\n", request.getRemoteAddr()));
        sw.append(String.format("type:{%s};\n", request.getMethod()));

//        sw.append(String.format("请求参数:{%s};\n", StartRun.objectMapper.writeValueAsString(request.getParameterMap())));
        sw.append(String.format("请求参数:{%s};\n", JsonMapper.toString(request.getParameterMap())));
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * 通用的表单提交验证异常处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ApiResult validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        String msg;
        if (bindResult != null && bindResult.hasErrors()) {
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
            if (msg.contains("NumberFormatException")) {
                msg = "参数类型错误！";
            }
        }else {
            msg = "系统繁忙，请稍后重试...";
        }
        return ApiResultUtils.error(-2,msg);
    }
}
