package com.ebsite.tempsite.ebsecurity.core.valcode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的图片验证码处理器
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 验证码的存放位置，web可以直接实现存放在session,app可以实现存放在redis
     */
    @Autowired
    protected ValidateCodeRepository validateCodeRepository;

    @Override
    public String allowMethod() {
        return "post";
    }
    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
     * springframework.web.context.request.ServletWebRequest)
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        //获取生成器并生成一个验证码对象
        C validateCode = generate(request);
        //保存验证码对象到session
        save(request, validateCode);
        //发送验证码
        send(request, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    abstract protected String getMobileEmail(ServletWebRequest request) ;
    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    protected void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType(request),getMobileEmail(request));
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    protected EmValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return EmValidateCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        EmValidateCodeType codeType = getValidateCodeType(request);
        String mobileEmail = getMobileEmail(request);
        if(StringUtils.isNotEmpty(mobileEmail)){
            String currentMobileEmail = validateCodeRepository.getCurrentEmailMobile(request);
            if(!mobileEmail.equals(currentMobileEmail)){
                throw new ValidateCodeException("手机号或Email被篡改，已向管理员汇报此异常!");
            }
        }
        C codeInSession = (C) validateCodeRepository.get(request, codeType,mobileEmail);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, codeType,getMobileEmail(request));
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            //validateCodeRepository.remove(request, codeType,getMobileEmail(request));
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, codeType,getMobileEmail(request));

    }

}
